CREATE TABLE professionals (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  specialty VARCHAR(120) NOT NULL
);
CREATE TABLE appointments (
  id BIGSERIAL PRIMARY KEY,
  professional_id BIGINT NOT NULL REFERENCES professionals(id),
  customer_name VARCHAR(120) NOT NULL,
  customer_email VARCHAR(254) NOT NULL,
  starts_at TIMESTAMP WITH TIME ZONE NOT NULL,
  ends_at TIMESTAMP WITH TIME ZONE NOT NULL,
  status VARCHAR(20) NOT NULL CHECK (status IN ('SCHEDULED','CANCELLED')),
  CONSTRAINT valid_interval CHECK (ends_at > starts_at)
);
CREATE INDEX idx_appointments_professional_time ON appointments(professional_id,starts_at,ends_at);
