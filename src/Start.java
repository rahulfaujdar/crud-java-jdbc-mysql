import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import com.user.dao.UserDao;
import com.user.model.User;

public class Start {
	static UserDao userDao;

	public static void main(String[] args) throws IOException, SQLException {
		userDao = new UserDao();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("Press 1 to Add User");
			System.out.println("Press 2 to Delete User");
			System.out.println("Press 3 to Display User");
			System.out.println("Press 4 to Display All User");
			System.out.println("Press 5 to Update User");
			System.out.println("Press 6 to Exit");

			int c = Integer.parseInt(br.readLine());

			switch (c) {
			case 1: {
				System.out.println("Enter Name: ");
				String name = br.readLine();

				System.out.println("Enter Mobile: ");
				String mobile = br.readLine();

				System.out.println("Enter Address: ");
				String address = br.readLine();

				User user = new User(name, mobile, address);
				boolean check = userDao.insertUser(user);
				if(check) {
					System.out.println("User Created");	
				}else {
					System.out.println("Something went wrong");
				}
				break;
			}
			case 2: {
				System.out.println("Enter Id: ");
				int id = Integer.parseInt(br.readLine());
				boolean check = userDao.deleteUser(id);
				if(check) {
					System.out.println("User Deleted");	
				}else {
					System.out.println("Something went wrong");
				}
				break;
			}
			case 3: {
				System.out.println("Enter Id: ");
				int id = Integer.parseInt(br.readLine());
				User user = userDao.selectUser(id);
				if(user != null) {
					System.out.println(user);	
				}else {
					System.out.println("Something went wrong");
				}
				break;
			}
			case 4: {
				List<User> users = userDao.selectUsers();
				if(users != null) {
					System.out.println(users);	
				}else {
					System.out.println("Something went wrong");
				}
				break;
			}
			case 5: {
				System.out.println("Enter Id: ");
				int id = Integer.parseInt(br.readLine());
				
				System.out.println("Enter Name: ");
				String name = br.readLine();

				System.out.println("Enter Mobile: ");
				String mobile = br.readLine();

				System.out.println("Enter Address: ");
				String address = br.readLine();

				User user = new User(id, name, mobile, address);
				boolean check = userDao.updateUser(user);
				if(check) {
					System.out.println("User Updated");	
				}else {
					System.out.println("Something went wrong");
				}
				break;
			}
			case 6: {
				return;
			}
			default:
				System.out.println("Invalid option");
			}
		}
	}

}
