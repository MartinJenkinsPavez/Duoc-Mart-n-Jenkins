TRUNCATE TABLE detalle_de_clientes;
VARIABLE b_periodo VARCHAR2(6);
VARIABLE b_mes     VARCHAR2(2);
EXEC :b_periodo := '032024';
EXEC :b_mes     := '03';

DECLARE
    v_id_cliente    cliente.id_cli%TYPE;
    v_renta         cliente.renta%TYPE;
    v_porcentaje    tramo_edad.porcentaje%TYPE;    
    v_edad          NUMBER; 
    v_puntaje       NUMBER := 0;
    v_correo        VARCHAR2(100);
    v_total_clientes NUMBER := 0;
    v_procesados     NUMBER := 0;
    CURSOR c_clientes IS
        SELECT 
            cl.id_cli AS idc,
            cl.numrun_cli AS rut,
            cl.pnombre_cli,
            cl.snombre_cli,
            cl.appaterno_cli,
            cl.apmaterno_cli,
            cl.fecha_nac_cli,
            cl.renta,
            com.nombre_comuna,
            tc.nombre_tipo_cli
        FROM cliente cl
        JOIN comuna com ON cl.id_comuna = com.id_comuna
        JOIN tipo_cliente tc ON cl.id_tipo_cli = tc.id_tipo_cli;
        
BEGIN
    DBMS_OUTPUT.PUT_LINE('PROCESANDO CLIENTES ...');
    SELECT COUNT(*) INTO v_total_clientes FROM cliente;
    
    FOR r_cliente IN c_clientes LOOP
        v_puntaje := 0;
        v_edad := TRUNC(MONTHS_BETWEEN(SYSDATE, r_cliente.fecha_nac_cli) / 12);        
        IF r_cliente.renta > 800000 AND r_cliente.nombre_comuna NOT IN ('La Reina', 'Las Condes', 'Vitacura') THEN
            v_puntaje := ROUND(r_cliente.renta * 0.03);
            
        ELSIF r_cliente.nombre_tipo_cli IN ('Extranjero', 'Vip', 'Internacional') THEN
            v_puntaje := ROUND(v_edad * 30);
        END IF;
        
        IF v_puntaje = 0 THEN
            BEGIN
                SELECT porcentaje 
                INTO v_porcentaje 
                FROM tramo_edad 
                WHERE v_edad BETWEEN tramo_inf AND tramo_sup
                  AND anno_vig = EXTRACT(YEAR FROM SYSDATE);
                  
                v_puntaje := ROUND(r_cliente.renta * (v_porcentaje / 100));
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    v_puntaje := 0;
            END;
        END IF;
        
        v_correo := LOWER(r_cliente.appaterno_cli) || 
                    v_edad || 
                    '*' || 
                    UPPER(SUBSTR(r_cliente.pnombre_cli, 1, 1)) || 
                    TO_CHAR(r_cliente.fecha_nac_cli, 'DD') || 
                    TO_NUMBER(:b_mes) || 
                    '@LogiCarg.cl';
                    
        INSERT INTO detalle_de_clientes (
            idc, rut, cliente, edad, puntaje, correo_corp, periodo
        ) VALUES (
            r_cliente.idc, 
            r_cliente.rut, 
            r_cliente.appaterno_cli || ' ' || r_cliente.apmaterno_cli || ' ' || r_cliente.pnombre_cli || ' ' || NVL(r_cliente.snombre_cli, ''), 
            v_edad, 
            v_puntaje, 
            v_correo, 
            SUBSTR(:b_periodo, 1, 2) || '/' || SUBSTR(:b_periodo, 3, 4)
        );
        
        v_procesados := v_procesados + 1;
    END LOOP;
    
    IF v_procesados = v_total_clientes THEN
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Proceso Finalizado Exitosamente');
        DBMS_OUTPUT.PUT_LINE('Se Procesaron : ' || v_procesados || ' CLIENTES');
    ELSE
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Proceso Finalizado CON ERRORES - DESHACIENDO TRANSACCIONES');
    END IF;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('PROCESANDO CLIENTES ...');
        DBMS_OUTPUT.PUT_LINE('Proceso Finalizado CON ERRORES - DESHACIENDO TRANSACCIONES');
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/