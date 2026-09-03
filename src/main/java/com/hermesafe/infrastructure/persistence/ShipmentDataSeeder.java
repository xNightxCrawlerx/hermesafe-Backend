package com.hermesafe.infrastructure.persistence;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipmentDataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ShipmentDataSeeder.class);
    private final ShipmentRepository shipmentRepository;

    public ShipmentDataSeeder(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public void run(String... args) {
        if (shipmentRepository.count() == 0) {
            log.info("[Hermesafe] Seeding initial 10 shipments into PostgreSQL database...");
            List<Shipment> initial = List.of(
                    new Shipment("ENV-1001", "HMS-849201-CL", "TechSolutions Chile SpA", "Ignacio Morales Vera", "Santiago", "Concepción", "IN_TRANSIT", "EXPRESS", 4.5, "2026-08-08", "2026-08-06", "Servidores de alta fidelidad. Manejar con extremo cuidado.", true),
                    new Shipment("ENV-1002", "HMS-930122-CL", "Librería Central", "Valentina Henríquez", "Valparaíso", "La Serena", "DELIVERED", "STANDARD", 1.2, "2026-08-05", "2026-08-03", "Entregado a recepción del edificio.", false),
                    new Shipment("ENV-1003", "HMS-721094-CL", "FarmaMedix Chile", "Dr. Roberto Silva", "Santiago", "Temuco", "IN_TRANSIT", "OVERNIGHT", 0.8, "2026-08-07", "2026-08-06", "Cadena de frío controlada (2-8°C).", false),
                    new Shipment("ENV-1004", "HMS-615843-CL", "AgroInsumos del Sur", "Camila Sepúlveda Soto", "Rancagua", "Talca", "PENDING", "STANDARD", 15.0, "2026-08-10", "2026-08-06", "Semillas certificadas temporada primavera.", false),
                    new Shipment("ENV-1005", "HMS-504938-CL", "ElectroChile Retail", "Esteban Carrasco", "Santiago", "Antofagasta", "IN_TRANSIT", "EXPRESS", 8.3, "2026-08-09", "2026-08-05", "Equipos de telecomunicaciones mineras.", false),
                    new Shipment("ENV-1006", "HMS-493820-CL", "Artesanías del Valle", "Pilar Valenzuela", "La Serena", "Santiago", "DELIVERED", "FRAGILE", 2.1, "2026-08-04", "2026-08-02", "Cerámicas decorativas esmaltadas.", false),
                    new Shipment("ENV-1007", "HMS-382910-CL", "Textiles Araucanía", "Gonzalo Muñoz Paredes", "Temuco", "Puerto Montt", "PENDING", "STANDARD", 6.0, "2026-08-11", "2026-08-06", "Rollos de tela industrial para confección.", false),
                    new Shipment("ENV-1008", "HMS-271829-CL", "BioLab Diagnóstica", "Clínica Los Andes", "Concepción", "Valdivia", "IN_TRANSIT", "OVERNIGHT", 0.5, "2026-08-07", "2026-08-06", "Reactivos de laboratorio para análisis clínico.", false),
                    new Shipment("ENV-1009", "HMS-160738-CL", "Maquinarias del Norte", "Minera Escondida", "Iquique", "Calama", "CANCELLED", "STANDARD", 28.5, "2026-08-06", "2026-08-04", "Cancelado por el cliente antes de la consolidación de carga.", false),
                    new Shipment("ENV-1010", "HMS-059483-CL", "Editorial Universitaria", "Prof. Maritza Donoso", "Valparaíso", "Chillán", "DELIVERED", "STANDARD", 3.4, "2026-08-03", "2026-08-01", "Textos de estudio para departamento de ingeniería.", false)
            );

            for (Shipment s : initial) {
                shipmentRepository.save(s);
            }
            log.info("[Hermesafe] Successfully seeded {} shipments into database.", initial.size());
        }
    }
}
