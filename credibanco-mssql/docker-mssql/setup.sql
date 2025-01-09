IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'credibanco')
BEGIN
    EXEC('CREATE DATABASE credibanco;');
END

GO

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'product_sequence' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE SEQUENCE dbo.product_sequence START WITH 100000 INCREMENT BY 1;
END;