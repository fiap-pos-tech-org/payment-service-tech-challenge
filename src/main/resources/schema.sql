CREATE TABLE IF NOT EXISTS cobranca (
    id bigint NOT NULL AUTO_INCREMENT,
    pedido_id bigint NOT NULL,
    status enum ('PENDENTE', 'PAGO', 'CANCELADO') NOT NULL,
    valor double NOT NULL,
    qr_code text NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    primary key (id),
    index(status)
);