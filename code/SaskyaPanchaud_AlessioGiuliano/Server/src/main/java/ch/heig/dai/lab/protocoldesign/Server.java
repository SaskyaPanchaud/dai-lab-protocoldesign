package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.*;

public class Server {
    final int SERVER_PORT = 1234;

    enum OperationType {UNARY, BINARY, NARY, INVALID};
    enum Operation {ADD, SUBSTRACT, MULTIPLY, DIVIDE, INVALID};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private String result(double result)
    {
        return "RESULT " + String.valueOf(result);
    }

    private String invalidOperation(String name)
    {
        return "INVALID OPERATION " + name;
    }

    private String invalidOperandsCount(int count)
    {
        return "INVALID OPERANDS COUNT " + String.valueOf(count);
    }

    private String invalidOperand(String operand)
    {
        return "INVALID OPERAND " + operand;
    }

    private String zeroDivision()
    {
        return "ZERO DIVISION";
    }

    private String unknownError(Exception e)
    {
        return "UNKNOWN ERROR " + e.toString();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT))
        {
            while (true) 
            {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                     )
                {
                    String result = "";
                    String line;
                    while ((line = in.readLine()) != null)
                    {
                        try
                        {
                            System.out.println(line);
                            String[] args = line.split("[ ]");

                            Operation operation = getOperation(args[0]);

                            if (operation == Operation.INVALID)
                            {
                                result = invalidOperation(args[0]);
                                continue;
                            }
                            else 
                            {
                                try 
                                {
                                    var operands = getOperands(args);
                                    result = operate(operation, operands);
                                }
                                catch (NumberFormatException e)
                                {
                                    result = e.getMessage();
                                }
                            }
                    }
                    catch (Exception e)
                    {
                        result = unknownError(e);
                    }
                    finally
                    {
                        out.write(result + "\n");
                        out.flush();
                    }
                }
                }
                catch(IOException e)
                {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
    
    // Convert the string operation to an enum type, allowing to change the name of the operation easily.
    private Operation getOperation(String operation)
    {
        switch(operation)
        {
            case "ADD":
                return Operation.ADD;
            case "SUBSTRACT":
                return Operation.SUBSTRACT;
            case "MULTIPLY":
                return Operation.MULTIPLY;
            case "DIVIDE":
                return Operation.DIVIDE;
            default:
                return Operation.INVALID;
        }
    }

    private OperationType getOperationType(Operation operation)
    {
        switch (operation)
        {
            case ADD:
            case SUBSTRACT:
            case MULTIPLY:
            case DIVIDE:
                return OperationType.BINARY;
            default:
                return OperationType.INVALID;
        }
    }

    private double[] getOperands(String[] args)
    {
        var result = new double[args.length - 1];
        for (int i = 1; i < args.length; ++i)
        {   
            try
            {
                result[i - 1] = Double.parseDouble(args[i]);
            }
            catch (NumberFormatException e)
            {
                throw new NumberFormatException(invalidOperand(args[i]));
            }
        }
        return result;
    }

    private int getOperandsCount(OperationType type)
    {
        switch (type)
        {
            case UNARY:
                return 1;
            case BINARY:
                return 2;
            case NARY:
                return -1;
            default:
                throw new RuntimeException("Invalid operation type");
        }
    }

    private String operate(Operation operation, double ... operands)
    {
        OperationType type = getOperationType(operation);
        int operandsCount = getOperandsCount(type);
        if (operandsCount != -1 && operandsCount != operands.length)
        {
            return invalidOperandsCount(operands.length);
        }

        double result = 0;

        try
        {
            switch (type)
            {
                case UNARY:
                    // TODO Implement or remove
                    break;
                case BINARY:
                    result = binaryOperation(operands[0], operands[1], operation);
                    break;
                case NARY:
                    // TODO Implement or remove
                    break;
                default:
                    break;
            }
        }
        catch (ArithmeticException e)
        {
            return zeroDivision();
        }

        return result(result);
    }

    private double binaryOperation(double lhs, double rhs, Operation operation)
    {
        switch (operation)
        {
            case ADD:
                return lhs + rhs;
            case SUBSTRACT:
                return lhs - rhs;
            case MULTIPLY:
                return lhs * rhs;
            case DIVIDE:
                if (rhs == 0)
                {
                    throw new ArithmeticException("Zero division");
                }
                return lhs / rhs;
            default:
                throw new RuntimeException("Invalid operation");
        }
    }
}