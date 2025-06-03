package bankboston.util;

import java.util.Scanner;

public class InputValidator {
    

    public static int readValidInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Entrada vacía. Por favor ingrese un número: ");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida, debe ingresar solo números. Intente otra vez: ");
            }
        }
    }

    public static String readValidString(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Entrada vacía. Por favor ingrese un texto: ");
                continue;
            }
            try {
                Double.parseDouble(input);
                System.out.print("Entrada inválida. No se permiten números. Intente otra vez: ");
            } catch (NumberFormatException e) {
                return input;
            }
        }
    }

    public static boolean isValidChileanRUT(String rut) {
        rut = rut.replace(".", "").replace("-", "").toUpperCase();
        if (rut.length() < 2) return false;

        String numberPart = rut.substring(0, rut.length() - 1);
        char givenDV = rut.charAt(rut.length() - 1);

        try {
            int rutNum = Integer.parseInt(numberPart);
            return calculateRUTCheckDigit(rutNum) == givenDV;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static char calculateRUTCheckDigit(int rut) {
        int sum = 0, multiplier = 2;
        while (rut > 0) {
            sum += (rut % 10) * multiplier;
            rut /= 10;
            multiplier = (multiplier == 7) ? 2 : multiplier + 1;
        }
        int remainder = 11 - (sum % 11);
        return switch (remainder) {
            case 11 -> '0';
            case 10 -> 'K';
            default -> (char) (remainder + '0');
        };
    }
} 
