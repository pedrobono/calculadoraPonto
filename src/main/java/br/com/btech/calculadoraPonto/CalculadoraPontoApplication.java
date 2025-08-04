package br.com.btech.calculadoraPonto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CalculadoraPontoApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        System.out.println("=== CALCULADORA DE PONTO ===");
        
        System.out.print("Digite o horário de entrada (HH:mm): ");
        LocalTime entrada1 = LocalTime.parse(scanner.nextLine(), formatter);
        
        System.out.print("Digite o horário de saída para almoço (ou Enter se ainda não saiu): ");
        String saidaAlmocoStr = scanner.nextLine();
        LocalTime saidaAlmoco = saidaAlmocoStr.isEmpty() ? null : LocalTime.parse(saidaAlmocoStr, formatter);
        
        System.out.print("Digite o horário de volta do almoço (ou Enter se ainda não voltou): ");
        String voltaAlmocoStr = scanner.nextLine();
        LocalTime voltaAlmoco = voltaAlmocoStr.isEmpty() ? null : LocalTime.parse(voltaAlmocoStr, formatter);
        
        calcularHorarios(entrada1, saidaAlmoco, voltaAlmoco, formatter);
        
        scanner.close();
    }
    
    private static void calcularHorarios(LocalTime entrada1, LocalTime saidaAlmoco, LocalTime voltaAlmoco, DateTimeFormatter formatter) {
        LocalTime saidaNormal, saidaExtra;
        
        if (saidaAlmoco == null) {
            // Apenas entrada informada - prever tudo
            saidaAlmoco = LocalTime.of(12, 0);
            voltaAlmoco = LocalTime.of(13, 0);
            saidaNormal = entrada1.plusHours(9).plusMinutes(45); // 8h45 + 1h almoço
            saidaExtra = saidaNormal.plusHours(1);
            
            System.out.println("\n=== PREVISÃO COMPLETA ===");
            System.out.println("Entrada: " + entrada1.format(formatter));
            System.out.println("Saída almoço (prevista): " + saidaAlmoco.format(formatter));
            System.out.println("Volta almoço (prevista): " + voltaAlmoco.format(formatter));
            
        } else if (voltaAlmoco == null) {
            // Entrada e saída almoço informadas
            voltaAlmoco = saidaAlmoco.plusHours(1);
            long minutosAlmoco = java.time.Duration.between(saidaAlmoco, voltaAlmoco).toMinutes();
            saidaNormal = entrada1.plusHours(8).plusMinutes(45).plusMinutes(minutosAlmoco);
            saidaExtra = saidaNormal.plusHours(1);
            
            System.out.println("\n=== PREVISÃO PARCIAL ===");
            System.out.println("Entrada: " + entrada1.format(formatter));
            System.out.println("Saída almoço: " + saidaAlmoco.format(formatter));
            System.out.println("Volta almoço (prevista): " + voltaAlmoco.format(formatter));
            
        } else {
            // Todos os horários informados
            long minutosAlmoco = java.time.Duration.between(saidaAlmoco, voltaAlmoco).toMinutes();
            saidaNormal = entrada1.plusHours(8).plusMinutes(45).plusMinutes(minutosAlmoco);
            saidaExtra = saidaNormal.plusHours(1);
            
            System.out.println("\n=== CÁLCULO FINAL ===");
            System.out.println("Entrada: " + entrada1.format(formatter));
            System.out.println("Saída almoço: " + saidaAlmoco.format(formatter));
            System.out.println("Volta almoço: " + voltaAlmoco.format(formatter));
        }
        
        System.out.println("\n=== HORÁRIOS DE SAÍDA ===");
        System.out.println("Saída normal (8h45): " + saidaNormal.format(formatter));
        System.out.println("Saída com 1h extra (9h45): " + saidaExtra.format(formatter));
    }
}
