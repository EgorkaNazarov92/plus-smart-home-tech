CREATE TABLE IF NOT EXISTS shopping_cart (
  shopping_cart_id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
  username VARCHAR NOT NULL,
  cart_state boolean NOT NULL,
);

CREATE TABLE IF NOT EXISTS shopping_cart_products (
  product_id VARCHAR NOT NULL,
  quantity INTEGER,
  shopping_cart_id  uuid REFERENCES shopping_cart(shopping_cart_id) ON DELETE CASCADE
);