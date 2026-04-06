public class HelloApp {
    public static void main(String[] args) {
        String greetingNames;

        // Check if command-line arguments were provided
        if (args.length > 0) {
            // Join all names from the args array with commas and spaces
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(", ");
                }
            }
            greetingNames = sb.toString();
        } else {
            // Use default value if no arguments exist
            greetingNames = "World";
        }

        // Display the final personalized greeting
        System.out.println("Hello, " + greetingNames + "!");
    }
}
