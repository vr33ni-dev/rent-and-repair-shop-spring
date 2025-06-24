package com.example.shop.service;

 import com.example.shop.enums.BillStatus;
import com.example.shop.dto.BillResponseDTO;
 import com.example.shop.model.Bill;
import com.example.shop.model.Customer;
import com.example.shop.model.Rental;
import com.example.shop.model.Repair;
import com.example.shop.repository.BillingRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


 public class BillingService {

        private final BillingRepository billingRepository;
        private final CustomerRepository customerRepository;
        private final RentalRepository rentalRepository;
        private final RepairRepository repairRepository;

        public BillingService(BillingRepository billingRepository, CustomerRepository customerRepository,
                        RepairRepository repairRepository,
                        RentalRepository rentalRepository) {
                this.customerRepository = customerRepository;
                this.repairRepository = repairRepository;
                this.rentalRepository = rentalRepository;
                this.billingRepository = billingRepository;
        }

        public List<BillResponseDTO> getAllBillDTOs() {
                List<Bill> bills = billingRepository.findAll();

                return bills.stream().map(bill -> {
                        Optional<Customer> custOpt = customerRepository.findById(bill.getCustomerId());

                        String customerName = custOpt
                                        .map(Customer::getName)
                                        .orElse("Unknown Customer");

                        String customerContact = custOpt
                                        .map(Customer::getContact)
                                        .orElse("Unknown");

                        String customerContactType = custOpt
                                        .map(Customer::getContactType)
                                        .orElse("unknown");

                        LocalDateTime rentalDate = bill.getRentalId() != null
                                        ? rentalRepository.findById(bill.getRentalId())
                                                        .map(Rental::getRentedAt)
                                                        .orElse(null)
                                        : null;

                        LocalDateTime rentalReturnDate = bill.getRentalId() != null
                                        ? rentalRepository.findById(bill.getRentalId())
                                                        .map(Rental::getReturnedAt)
                                                        .orElse(null)
                                        : null;

                        LocalDateTime repairDate = bill.getRepairId() != null
                                        ? repairRepository.findById(bill.getRepairId())
                                                        .map(Repair::getCreatedAt)
                                                        .orElse(null)
                                        : null;

                        return new BillResponseDTO(
                                        bill.getId(),
                                        bill.getCustomerId(),
                                        customerName,
                                        customerContact,
                                        customerContactType,
                                        bill.getDescription(),
                                        bill.getRentalId(),
                                        bill.getRepairId(),
                                        bill.getRentalFee(),
                                        bill.getRepairFee(),
                                        bill.getTotalAmount(),
                                        bill.getStatus(),
                                        bill.getCreatedAt(),
                                        bill.getPaidAt(),
                                        rentalDate,
                                        rentalReturnDate,
                                        repairDate);
                }).collect(Collectors.toList());
        }

        public void markBillAsPaid(UUID id) {
                Bill bill = billingRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Bill not found: " + id));

                bill.setStatus(BillStatus.PAID);
                bill.setPaidAt(LocalDateTime.now());
                billingRepository.save(bill);
        }

}
