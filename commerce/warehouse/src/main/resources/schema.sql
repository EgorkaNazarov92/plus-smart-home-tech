CREATE TABLE IF NOT EXISTS warehouse_product (
    product_id VARCHAR PRIMARY KEY,
    quantity   INTEGER,
    fragile    BOOLEAN,
    weight     double precision NOT NULL
);

CREATE TABLE IF NOT EXISTS dimension_product (
    product_id VARCHAR REFERENCES warehouse_product(product_id) ON DELETE CASCADE,
    width      double precision NOT NULL,
    height     double precision NOT NULL,
    depth      double precision NOT NULL
);