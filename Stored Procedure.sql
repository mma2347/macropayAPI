-- Cambiar el delimitador para definir el procedimiento
DELIMITER //

CREATE PROCEDURE CobroAutomatico()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE clientId VARCHAR(255);
    DECLARE loanId INT;
    DECLARE loanAmount DECIMAL(10, 2);
    DECLARE loanDate DATE;
    DECLARE accountAmount DECIMAL(10, 2);
    DECLARE interest DECIMAL(10, 2);
    DECLARE iva DECIMAL(10, 2);
    DECLARE paymentAmount DECIMAL(10, 2);
    DECLARE sucursalIVA DECIMAL(4, 2);
    DECLARE plazo INT;
    DECLARE tasaInteres DECIMAL(4, 2) DEFAULT 0.05; -- Tasa de interés del 5%
    DECLARE diasAnioComercial INT DEFAULT 360;

    -- Cursor para recorrer las cuentas activas
    DECLARE loan_cursor CURSOR FOR
        SELECT l.Client, l.Id, l.Amount, l.Date_Loan, a.Amount, s.IVA
        FROM loans l
        JOIN Accounts a ON l.Client = a.Client
        JOIN Sucursales s ON l.IdSucursal = s.ID
        WHERE l.Status = 'Pendiente' AND a.Status = 'Activa'
        ORDER BY l.Date_Loan;

    -- Handler para terminar el cursor
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Abrir el cursor
    OPEN loan_cursor;

    read_loop: LOOP
        FETCH loan_cursor INTO clientId, loanId, loanAmount, loanDate, accountAmount, sucursalIVA;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Calcular el plazo
        SET plazo = DATEDIFF(CURDATE(), loanDate);

        -- Calcular interés
        SET interest = ROUND((loanAmount * plazo * tasaInteres) / diasAnioComercial, 2);

        -- Calcular IVA
        SET iva = ROUND(interest * (sucursalIVA / 100), 2);

        -- Calcular monto total del pago
        SET paymentAmount = loanAmount + interest + iva;

        -- Validar si hay suficiente saldo en la cuenta para hacer el cobro
        IF accountAmount >= paymentAmount THEN
            -- Actualizar el préstamo como pagado
            UPDATE loans
            SET Status = 'Pagado'
            WHERE Client = clientId AND Id = loanId;

            -- Descontar el pago del saldo de la cuenta
            UPDATE Accounts
            SET Amount = Amount - paymentAmount
            WHERE Client = clientId;

            -- (Opcional) Insertar registro en tabla de auditoría
            INSERT INTO CobrosAuditoria (Client, LoanId, MontoCobrado, FechaCobro)
            VALUES (clientId, loanId, paymentAmount, CURDATE());
        END IF;
    END LOOP;

    -- Cerrar el cursor
    CLOSE loan_cursor;

    -- Confirmar la transacción
    COMMIT;

END //

-- Restaurar el delimitador a ;
DELIMITER ;

CREATE TABLE CobrosAuditoria (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Client VARCHAR(255),
    LoanId INT,
    MontoCobrado DECIMAL(10, 2),
    FechaCobro DATE
);

CALL CobroAutomatico();
SELECT * FROM loans;
SELECT * FROM CobrosAuditoria;
