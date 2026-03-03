package com.devsenior.diego.bibliokeep.scheduled;

import com.devsenior.diego.bibliokeep.model.entity.Loan;
import com.devsenior.diego.bibliokeep.repository.BookRepository;
import com.devsenior.diego.bibliokeep.repository.LoanRepository;
import com.devsenior.diego.bibliokeep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Componente programado (Scheduler) que verifica periódicamente los préstamos vencidos
 * y prepara notificaciones para los propietarios de los libros.
 * 
 * Esta clase utiliza la anotación @Scheduled de Spring para ejecutar tareas automáticas
 * en intervalos de tiempo definidos mediante expresiones cron.
 * 
 * @author Diego
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoanNotificationScheduler {

    /**
     * Repositorio para acceder a los datos de préstamos en la base de datos.
     */
    private final LoanRepository loanRepository;
    
    /**
     * Repositorio para acceder a los datos de libros en la base de datos.
     */
    private final BookRepository bookRepository;
    
    /**
     * Repositorio para acceder a los datos de usuarios en la base de datos.
     */
    private final UserRepository userRepository;

    /**
     * Método programado que se ejecuta automáticamente todos los días a las 8:00 AM
     * para verificar y procesar préstamos vencidos.
     * 
     * Expresión cron: "0 0 8 * * *"
     * - 0: segundo 0
     * - 0: minuto 0
     * - 8: hora 8 (8:00 AM)
     * - *: cualquier día del mes
     * - *: cualquier mes
     * - *: cualquier día de la semana
     * 
     * Flujo de ejecución:
     * 1. Obtiene la fecha actual
     * 2. Busca todos los préstamos vencidos hasta la fecha actual
     * 3. Para cada préstamo vencido:
     *    - Obtiene la información del libro prestado
     *    - Obtiene la información del usuario propietario del libro
     *    - Calcula los días de retraso
     *    - Registra la información en el log (preparado para envío de notificaciones)
     * 
     * Nota: Actualmente solo registra la información en logs. El envío de correos
     * electrónicos está pendiente de implementación.
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void checkOverdueLoans() {
        log.info("Iniciando verificación de préstamos vencidos...");
        
        // Obtiene la fecha actual para comparar con las fechas de vencimiento
        var today = LocalDate.now();
        
        // Busca todos los préstamos cuya fecha de vencimiento es anterior a hoy
        var overdueLoans = loanRepository.findOverdueLoans(today);
        
        // Si no hay préstamos vencidos, finaliza la ejecución
        if (overdueLoans.isEmpty()) {
            log.info("No se encontraron préstamos vencidos.");
            return;
        }

        log.info("Se encontraron {} préstamo(s) vencido(s).", overdueLoans.size());

        // Procesa cada préstamo vencido individualmente
        for (Loan loan : overdueLoans) {
            try {
                // Obtiene la información del libro asociado al préstamo
                var book = bookRepository.findById(loan.getBookId());
                
                // Valida que el libro exista en la base de datos
                if (book.isEmpty()) {
                    log.warn("Libro con ID {} no encontrado para el préstamo {}", loan.getBookId(), loan.getId());
                    continue; // Salta al siguiente préstamo si el libro no existe
                }

                // Obtiene el ID del propietario del libro para notificarlo
                var ownerId = book.get().getOwnerId();
                var user = userRepository.findById(ownerId);
                
                // Valida que el usuario propietario exista en la base de datos
                if (user.isEmpty()) {
                    log.warn("Usuario con ID {} no encontrado para el préstamo {}", ownerId, loan.getId());
                    continue; // Salta al siguiente préstamo si el usuario no existe
                }

                // Extrae la información necesaria para la notificación
                var userEmail = user.get().getEmail();
                var contactName = loan.getContactName(); // Nombre de la persona que tiene el libro prestado
                var bookTitle = book.get().getTitle();
                
                // Calcula los días de retraso: diferencia entre hoy y la fecha de vencimiento
                var daysOverdue = today.toEpochDay() - loan.getDueDate().toEpochDay();

                // Registra toda la información relevante del préstamo vencido
                log.info("""
                    Enviando notificación de mora:
                    - Usuario: {} ({})
                    - Libro: {}
                    - Contacto: {}
                    - Días de retraso: {}
                    - Fecha de vencimiento: {}
                    """, userEmail, ownerId, bookTitle, contactName, daysOverdue, loan.getDueDate());

                // TODO: Implementar envío de correo usando JavaMailSender cuando esté disponible
                // Por ahora solo se registra en el log
                // En el futuro, aquí se enviaría un correo al propietario del libro
                // informándole que su libro está vencido y quién lo tiene
                
            } catch (Exception e) {
                // Captura cualquier excepción inesperada para evitar que un error
                // en un préstamo detenga el procesamiento de los demás
                log.error("Error al procesar préstamo vencido con ID {}: {}", loan.getId(), e.getMessage(), e);
            }
        }

        log.info("Finalizada verificación de préstamos vencidos.");
    }
}
