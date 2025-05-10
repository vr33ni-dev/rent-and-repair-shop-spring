package com.example.shop.service;

import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RepairRequest;
import com.example.shop.dto.RepairResponseDTO;
import com.example.shop.model.Customer;
import com.example.shop.model.Repair;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final SurfboardRepository surfboardRepository;
    private final CustomerRepository customerRepository;
    private final RabbitTemplate rabbitTemplate;

    public RepairService(RepairRepository repairRepository, SurfboardRepository surfboardRepository,
            CustomerRepository customerRepository,
            RabbitTemplate rabbitTemplate) {
        this.repairRepository = repairRepository;
        this.surfboardRepository = surfboardRepository;
        this.customerRepository = customerRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<RepairResponseDTO> getAllRepairs() {
        List<Repair> repairs = repairRepository.findAll();

        return repairs.stream().map(repair -> {
            Surfboard board = surfboardRepository.findById(repair.getSurfboardId())
                    .orElseThrow(
                            () -> new IllegalStateException("Surfboard not found for repair ID: " + repair.getId()));

            String boardName = board.getName();
            String customerName = "Shop";

            if (repair.getCustomerId() != null) {
                customerName = customerRepository.findById(repair.getCustomerId())
                        .map(Customer::getName)
                        .orElse("Unknown Customer");
            }

            return new RepairResponseDTO(
                    repair.getId(),
                    board.getId(),
                    boardName,
                    repair.getIssue(),
                    repair.getStatus(),
                    repair.getCreatedAt(),
                    customerName);
        }).collect(Collectors.toList());
    }

    public void createRepair(RepairRequest request) {
        Long boardId = request.getSurfboardId();

        boolean repairExists = repairRepository
                .findBySurfboardIdAndStatusNot(boardId, "COMPLETED")
                .stream()
                .anyMatch(r -> !"CANCELED".equalsIgnoreCase(r.getStatus()));

        if (repairExists) {
            System.out.println("⚠️ Repair already exists for surfboard ID: " + boardId + ", skipping.");
            return;
        }
        if (request.getCustomerId() != null) {
            customerRepository.findById(request.getCustomerId())
                    .orElseGet(() -> {
                        Customer newCustomer = new Customer();
                        newCustomer.setId(request.getCustomerId());
                        newCustomer.setName("Customer " + request.getCustomerId());
                        return customerRepository.save(newCustomer);
                    });
        }
        Repair repair = new Repair();
        repair.setSurfboardId(request.getSurfboardId());
        repair.setIssue(request.getIssue());
        repair.setStatus("CREATED");
        repairRepository.save(repair);
        System.out.println("Manual repair created with ID: " + repair.getId());
    }

    public void markRepairAsCompleted(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new IllegalArgumentException("Repair not found with ID: " + repairId));

        // Update repair status
        repair.setStatus("COMPLETED");
        repairRepository.save(repair);

        surfboardRepository.findById(repair.getSurfboardId()).ifPresent(board -> {
            if (board.isShopOwned()) {
                board.setDamaged(false);
                board.setAvailable(true);
                surfboardRepository.save(board);
                System.out.println("🔧 Shop board marked as repaired: " + board.getId());
            } else {
                System.out.println("🔧 User-owned board repair completed (no status update on surfboard)");
            }
        });

        // Emit repair.completed message (customerId optional in RepairMessage if
        // tracked)
        RepairMessage msg = new RepairMessage(repair.getSurfboardId(), repair.getIssue(), repair.getCustomerId());
        rabbitTemplate.convertAndSend("surfboard.exchange", "repair.completed", msg);
        System.out.println("Repair.completed sent for board ID: " + repair.getSurfboardId());
    }

    public void cancelRepair(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new IllegalArgumentException("Repair not found with ID: " + repairId));

        if ("COMPLETED".equalsIgnoreCase(repair.getStatus())) {
            throw new IllegalStateException("Cannot cancel a completed repair.");
        }

        repair.setStatus("CANCELED");
        repairRepository.save(repair);

        System.out.println("Repair canceled: ID " + repairId);
    }

    @RabbitListener(queues = "repair.queue")
    public void processRepair(RepairMessage message) {
        System.out.println("📥 Repair requested for board ID: " + message.getSurfboardId());

        Surfboard board = surfboardRepository.findById(message.getSurfboardId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Surfboard not found with ID: " + message.getSurfboardId()));

        boolean repairExists = repairRepository
                .findBySurfboardIdAndStatusNot(board.getId(), "COMPLETED")
                .stream()
                .anyMatch(r -> !"CANCELED".equalsIgnoreCase(r.getStatus()));

        if (repairExists) {
            System.out.println("⚠️ Repair already exists for surfboard ID: " + board.getId() + ", skipping.");
            return;
        }
        Long ownerId = message.getCustomerId();
        if (ownerId != null) {
            customerRepository.findById(ownerId)
                    .orElseGet(() -> {
                        Customer newCustomer = new Customer();
                        newCustomer.setId(ownerId);
                        newCustomer.setName("Customer " + ownerId);
                        return customerRepository.save(newCustomer);
                    });
        }

        Repair repair = new Repair();
        repair.setSurfboardId(message.getSurfboardId());
        repair.setIssue(message.getIssue());
        repair.setStatus("CREATED");
        repair.setCustomerId(message.getCustomerId());
        repairRepository.save(repair);
        System.out.println("Automatic repair created with ID: " + repair.getId());
    }

}
