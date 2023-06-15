package ch.bbw.pr.sospri;
import java.util.Scanner;

public class ChallengeResponse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Geheimes Passwort
        String password = "geheim";

        // Challenge
        System.out.print("Bitte geben Sie eine Challenge ein: ");
        String challenge = scanner.nextLine();

        // Response
        System.out.print("Bitte geben Sie die Response ein: ");
        String response = scanner.nextLine();

        // Berechne die erwartete Response
        String expectedResponse = challenge + password;

        // Überprüfe, ob die erwartete Response mit der eingegebenen Response übereinstimmt
        if (response.equals(expectedResponse)) {
            System.out.println("Challenge-Response erfolgreich.");
            // Hier kann man das vergessene Passwort zurücksetzen oder ändern
        } else {
            System.out.println("Challenge-Response fehlgeschlagen.");
        }
    }
}
