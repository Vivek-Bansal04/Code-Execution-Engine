package com.cce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CodeExecutionEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeExecutionEngineApplication.class, args);
//		String javaCode = "public class " + "TempCode" + " {\n" +
//				"    public static void main(String[] args) {\n" +
//				"        System.out.print(\"Hello World\")\n" + // Additional print statement
//				"    }\n" +
//				"}\n";

		String javaCode = "import java.util.Scanner;\n" +
				"public class TempCode {\n" +
				"    public static void main(String[] args) {\n" +
				"        Scanner scanner = new Scanner(System.in);\n" +
				"\n" +
				"        // Read the first number\n" +
				"        int number1 = scanner.nextInt();\n" +
				"\n" +
				"        // Read the second number\n" +
				"        int number2 = scanner.nextInt();\n" +
				"\n" +
				"        // Calculate the sum\n" +
				"        int sum = number1 + number2;\n" +
				"\n" +
				"        // Output the result\n" +
				"        System.out.println(\"The sum of \" + number1 + \" and \" + number2 + \" is: \" + sum);\n" +
				"    }\n" +
				"}";

		System.out.println(CodeExecutionImpl.execute(javaCode));
	}

}
