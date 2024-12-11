import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class CLI 
{
    private HashMap<String, Executable> commands;

    public CLI()
    {
        commands = new HashMap<>();
    }

    public void addCommand(String string, Executable executable)
    {
        commands.put(string, executable);
    }

    public void run(Scanner scanner)
    {
        System.out.println("\n");
        
        while (true) 
        {
            for (Map.Entry<String, Executable> entry : commands.entrySet()) 
            {
                String key = entry.getKey();
                String description = entry.getValue().getDescription();
                System.out.println("Command: " + key + ", Description: " + description);
            }

            System.out.print("> ");

            String command = scanner.nextLine();

            if(commands.containsKey(command))
            {
                commands.get(command).run();
            }
            else
            {
                System.out.println("Error: unknown option: " + command + "\n");
            }
        }
    }
}