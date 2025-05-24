CREATE TABLE settings (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  key TEXT NOT NULL UNIQUE,
  value TEXT NOT NULL
);

-- Insert the default rental fee
INSERT INTO settings (key, value) VALUES ('default_rental_fee', '15.0');
