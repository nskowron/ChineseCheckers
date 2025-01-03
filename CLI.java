import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class CLI 
{
    private HashMap<String, Executable> commands;

    public boolean active = true;

    public CLI()
    {
        commands = new HashMap<>();
        addCommand("help", new Executable() 
        {
            @Override
            public void run() 
            {
                for (Map.Entry<String, Executable> entry : commands.entrySet()) 
                {
                    String key = entry.getKey();
                    String description = entry.getValue().getDescription();
                    System.out.println("Command: " + key + "\n Description: " + description);
                }
            }

            @Override
            public String getDescription() 
            {
                return "Display commands";
            }
        });
    }

    public void addCommand(String string, Executable executable)
    {
        commands.put(string, executable);
    }
    public void greet()
    {
        System.out.println("\n");

        for (Map.Entry<String, Executable> entry : commands.entrySet()) 
        {
            String key = entry.getKey();
            String description = entry.getValue().getDescription();
            System.out.println("Command: " + key + "\n Description: " + description);
        }
        System.out.println();
    }

    public void runNoWhile(Scanner scanner)
    {
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

    public void run(Scanner scanner)
    {
        greet();
        while (true) 
        {
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
