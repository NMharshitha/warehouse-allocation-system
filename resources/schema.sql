CREATE DATABASE IF NOT EXISTS warehouse_allocation_system;

USE warehouse_allocation_system;


CREATE TABLE IF NOT EXISTS warehouse (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    location VARCHAR(200) NOT NULL,

    capacity INT NOT NULL,

    status VARCHAR(20),

    deleted BOOLEAN DEFAULT FALSE,

    created_at DATETIME,

    updated_at DATETIME
);



CREATE TABLE IF NOT EXISTS product (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(150) NOT NULL,

    sku VARCHAR(100) UNIQUE NOT NULL,

    total_stock INT NOT NULL,

    created_at DATETIME,

    updated_at DATETIME
);



CREATE TABLE IF NOT EXISTS warehouse_inventory (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    warehouse_id BIGINT NOT NULL,

    product_id BIGINT NOT NULL,

    total_quantity INT DEFAULT 0,

    reserved_quantity INT DEFAULT 0,

    available_quantity INT DEFAULT 0,

    version BIGINT,

    CONSTRAINT fk_inventory_warehouse
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouse(id),

    CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id)
        REFERENCES product(id),

    UNIQUE (warehouse_id, product_id)
);


CREATE TABLE IF NOT EXISTS allocation (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    allocation_reference VARCHAR(100) UNIQUE,

    product_id BIGINT,

    warehouse_id BIGINT,

    quantity INT,

    status VARCHAR(30),

    remarks TEXT,

    allocated_at DATETIME,

    CONSTRAINT fk_allocation_product
        FOREIGN KEY (product_id)
        REFERENCES product(id),

    CONSTRAINT fk_allocation_warehouse
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouse(id)
);




CREATE TABLE IF NOT EXISTS stock_transfer (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    reference_no VARCHAR(100) UNIQUE,

    source_warehouse_id BIGINT,

    target_warehouse_id BIGINT,

    product_id BIGINT,

    quantity INT,

    status VARCHAR(30),

    transfer_date DATETIME,

    CONSTRAINT fk_source_warehouse
        FOREIGN KEY (source_warehouse_id)
        REFERENCES warehouse(id),

    CONSTRAINT fk_target_warehouse
        FOREIGN KEY (target_warehouse_id)
        REFERENCES warehouse(id),

    CONSTRAINT fk_transfer_product
        FOREIGN KEY (product_id)
        REFERENCES product(id)
);




CREATE TABLE IF NOT EXISTS audit_log (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    operation VARCHAR(50),

    entity_name VARCHAR(100),

    entity_id BIGINT,

    performed_by VARCHAR(100),

    payload TEXT,

    remarks TEXT,

    performed_at DATETIME
);