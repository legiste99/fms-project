package za.ac.cput.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class FmsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmsServerApplication.class, args);
		//showMessageBox();
	}
/**

	public static void showMessageBox(){
		JFrame frame = new JFrame("Fms server");
		JOptionPane.showMessageDialog(frame, "Server is running!");
		System.exit(0);
	}
*/

}
