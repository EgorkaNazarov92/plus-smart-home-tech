CREATE TABLE IF NOT EXISTS warehouse_product (
    product_id VARCHAR PRIMARY KEY,
    quantity   INTEGER,
    fragile    BOOLEAN,
    width      double precision NOT NULL,
    height     double precision NOT NULL,
    depth      double precision NOT NULL,
    weight     double precision NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    order_id VARCHAR PRIMARY KEY,
    fragile    BOOLEAN,
    delivery_volume  double precision NOT NULL,
    delivery_weight  double precision NOT NULL,
    delivery_id VARCHAR
);

CREATE TABLE IF NOT EXISTS booking_products (
    order_id VARCHAR REFERENCES bookings(order_id) ON DELETE CASCADE,
    product_id VARCHAR,
    quantity   INTEGER
);