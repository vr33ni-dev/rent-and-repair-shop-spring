-- V1__initial_schema_and_data
CREATE SCHEMA IF NOT EXISTS surfshop;

SET search_path TO surfshop;

CREATE TABLE customer (
    id uuid NOT NULL,
    email character varying(255),
    name character varying(255),
    phone character varying(255),
    PRIMARY KEY (id)
);

CREATE TABLE surfboard (
    id uuid NOT NULL,
    available boolean NOT NULL,
    damaged boolean NOT NULL,
    description text,
    image_url character varying(255),
    name character varying(255),
    owner_id uuid,
    shop_owned boolean NOT NULL,
    size double precision,
    size_text character varying(255),
    PRIMARY KEY (id)
);

CREATE TABLE rental (
    id uuid NOT NULL,
    customer_id uuid,
    rental_fee double precision,
    rented_at timestamp(6) without time zone,
    returned_at timestamp(6) without time zone,
    status character varying(255) NOT NULL,
    surfboard_id uuid,
    CONSTRAINT rental_status_check CHECK (
      status IN ('CREATED', 'RETURNED', 'BILLED')
    ),
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (surfboard_id) REFERENCES surfboard (id)
);

CREATE TABLE repair (
    id uuid NOT NULL,
    completed_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone,
    customer_id uuid,
    issue character varying(255),
    rental_id uuid,
    repair_fee double precision,
    status character varying(255) NOT NULL,
    surfboard_id uuid,
    CONSTRAINT repair_status_check CHECK (
      status IN ('CREATED', 'COMPLETED', 'CANCELED')
    ),
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (rental_id) REFERENCES rental (id),
    FOREIGN KEY (surfboard_id) REFERENCES surfboard (id)
);

CREATE TABLE bill (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone,
    customer_id uuid,
    description character varying(255),
    paid_at timestamp(6) without time zone,
    rental_fee double precision,
    rental_id uuid,
    repair_fee double precision,
    repair_id uuid,
    status character varying(255) NOT NULL,
    total_amount double precision,
    CONSTRAINT bill_status_check CHECK (
      status IN ('OPEN', 'COMPLETED', 'PAID')
    ),
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (rental_id) REFERENCES rental (id),
    FOREIGN KEY (repair_id) REFERENCES repair (id)
);
