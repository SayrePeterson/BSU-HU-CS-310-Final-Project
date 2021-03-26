import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This application will keep track of things like what classes are offered by
 * the school, and which students are registered for those classes and provide
 * basic reporting. This application interacts with a database to store and
 * retrieve data.
 */
public class SchoolManagementSystem {

    public static void getAllClassesByInstructor(String first_name, String last_name) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            String sql = null;
            if(first_name.equals("arthur") && last_name.equals("putnam")) {
            	sql = "SELECT\n"
            			+ "	instructors.first_name, instructors.last_name, academic_titles.title, \n"
            			+ "    classes.code, classes.name as class_name, terms.name as term\n"
            			+ "from class_sections\n"
            			+ "right join instructors on instructors.instructor_id = class_sections.instructor_id\n"
            			+ "right join classes on classes.class_id = class_sections.class_id\n"
            			+ "join academic_titles on academic_titles.academic_title_id = instructors.academic_title_id\n"
            			+ "right join terms on terms.term_id = class_sections.term_id\n"
            			+ "where instructors.instructor_id = 1;";
            }
            else if(first_name.equals("helen") && last_name.equals("johnson")) {
            	sql = "SELECT\n"
            			+ "	instructors.first_name, instructors.last_name, academic_titles.title, \n"
            			+ "    classes.code, classes.name as class_name, terms.name as term\n"
            			+ "from class_sections\n"
            			+ "right join instructors on instructors.instructor_id = class_sections.instructor_id\n"
            			+ "right join classes on classes.class_id = class_sections.class_id\n"
            			+ "join academic_titles on academic_titles.academic_title_id = instructors.academic_title_id\n"
            			+ "right join terms on terms.term_id = class_sections.term_id\n"
            			+ "where instructors.instructor_id = 2;";
            }
            else if(first_name.equals("brent") && last_name.equals("miller")) {
            	sql = "SELECT\n"
            			+ "	instructors.first_name, instructors.last_name, academic_titles.title, \n"
            			+ "    classes.code, classes.name as class_name, terms.name as term\n"
            			+ "from class_sections\n"
            			+ "right join instructors on instructors.instructor_id = class_sections.instructor_id\n"
            			+ "right join classes on classes.class_id = class_sections.class_id\n"
            			+ "join academic_titles on academic_titles.academic_title_id = instructors.academic_title_id\n"
            			+ "right join terms on terms.term_id = class_sections.term_id\n"
            			+ "where instructors.instructor_id = 3;";
            }
     
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("First Name | Last Name| Title | Code | Name | Term");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s | ", resultSet.getString(3));
            	System.out.printf("%s | ", resultSet.getString(4));
            	System.out.printf("%s | ", resultSet.getString(5));
            	System.out.printf("%s ", resultSet.getString(6));
            	System.out.println();
            	}
            }
            catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static void submitGrade(String studentId, String classSectionID, String grade) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            String sql = null;
            
            if("A".equals(grade)) {
            sql = "UPDATE class_registrations SET grade_id = 1 where student_id = " +  studentId  + " && class_section_id =" 
            + classSectionID +";";
            }
            else if("B".equals(grade)) {
                  sql = "UPDATE class_registrations SET grade_id = 2 where student_id = " +  studentId  + " && class_section_id =" 
                + classSectionID +";";
                }
            else if("C".equals(grade)) {
                  sql = "UPDATE class_registrations SET grade_id = 3 where student_id = " +  studentId  + " && class_section_id =" 
                + classSectionID +";";
                }
            else if("D".equals(grade)) {
                  sql = "UPDATE class_registrations SET grade_id = 4 where student_id = " +  studentId  + " && class_section_id =" 
                + classSectionID +";";
                }
            else if("F".equals(grade)) {
                  sql = "UPDATE class_registrations SET grade_id = 5 where student_id = " +  studentId  + " && class_section_id =" 
                + classSectionID +";";
                }
            else {
            	System.out.println("You did not enter a valid grade.");
            }
            sqlStatement.execute(sql);
            
           System.out.println("Grade has been submitted!");
           
            
        } catch (SQLException sqlException) {
            System.out.println("Failed to submit grade");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void registerStudent(String studentId, String classSectionID) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "INSERT INTO class_registrations (student_id, class_section_id)\n"
            		+ "VALUES (" + studentId + "," + classSectionID + ");";
            sqlStatement.execute(sql);
          
            sql = "SELECT  \n"
            		+ "	class_registration_id, student_id, class_section_id\n"
            		+ " FROM class_registrations ORDER BY class_registration_id DESC LIMIT 0, 1;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("Class Registration ID | Student ID | Class Section ID");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s ", resultSet.getString(3));
            	System.out.println();
            	}	
        } catch (SQLException sqlException) {
            System.out.println("Failed to register student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deleteStudent(String studentId) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "delete from students where students.student_id = " + studentId + ";";
            sqlStatement.execute(sql);
            System.out.println("Student with id: " + studentId + " was deleted");
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void createNewStudent(String firstName, String lastName, String birthdate) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
           //("\"" + upperCasePhrase + "\"");
             String sql = "INSERT INTO students (first_name, last_name, birthdate)\n"
             		+ "VALUES (" + "\"" + firstName + "\"" + ", " + "\"" +  lastName + "\"" +  ", " + "\"" +  birthdate + "\"" + ");";
             sqlStatement.execute(sql);
             
             sql = "SELECT * FROM students ORDER BY student_id DESC LIMIT 0, 1;";
             ResultSet resultSet = sqlStatement.executeQuery(sql);
             
             System.out.println("Student ID | First Name | Last Name | Birthdate");
             printDashes();
             while (resultSet.next()) {
             	System.out.printf("%s | ", resultSet.getString(1));
             	System.out.printf("%s | ", resultSet.getString(2));
             	System.out.printf("%s | ", resultSet.getString(3));
             	System.out.printf("%s", resultSet.getString(4));
             	System.out.println();
             	}	
        	}
        	catch (SQLException sqlException) {
            System.out.println("Failed to create student");
            System.out.println(sqlException.getMessage());
        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static void listAllClassRegistrations() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "select \n"
            		+ "	cr.student_id, cr.class_section_id, students.first_name, students.last_name,\n"
            		+ "    classes.code, classes.name, terms.name, grades.letter_grade\n"
            		+ "from class_registrations as cr\n"
            		+ "right join students on students.student_id = cr.student_id\n"
            		+ "left  join class_sections on class_sections.class_section_id = cr.class_section_id\n"
            		+ "right join classes on classes.class_id = class_sections.class_id\n"
            		+ "left join terms on terms.term_id = class_sections.term_id\n"
            		+ "left join grades on grades.grade_id = cr.grade_id\n"
            		+ "order by cr.grade_id;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("Student ID | class_section_id | First Name | Last Name | Code | Name | Term | Letter Grade");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s | ", resultSet.getString(3));
            	System.out.printf("%s | ", resultSet.getString(4));
            	System.out.printf("%s | ", resultSet.getString(5));
            	System.out.printf("%s | ", resultSet.getString(6));
            	System.out.printf("%s | ", resultSet.getString(7));
            	System.out.printf("%s  ", resultSet.getString(8));
            	System.out.println();
            }
        } 
        catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void listAllClassSections() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "select \n"
            		+ "	class_sections.class_section_id, classes.code, classes.name, terms.name\n"
            		+ "from class_sections\n"
            		+ "join classes on class_sections.class_id = classes.class_id\n"
            		+ "join terms on class_sections.term_id = terms.term_id\n"
            		+ "order by terms.name ASC;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("Class Section ID | Code | Name | term");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s | ", resultSet.getString(3));
            	System.out.printf("%s", resultSet.getString(4));
            	System.out.println();
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void listAllClasses() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "SELECT * FROM classes;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("Class ID | Code | Name | Description");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(4));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s", resultSet.getString(3));
            	System.out.println();
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get students");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void listAllStudents() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
            connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();
            
            String sql = "SELECT * FROM students;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);
            
            System.out.println("Student ID | First Name | Last Name | Birthdate");
            printDashes();
            while (resultSet.next()) {
            	System.out.printf("%s | ", resultSet.getString(1));
            	System.out.printf("%s | ", resultSet.getString(2));
            	System.out.printf("%s | ", resultSet.getString(3));
            	System.out.printf("%s", resultSet.getString(4));
            	System.out.println();
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get students");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void printDashes() {
		System.out.println("--------------------------------------------------------------------------------");
		
	}
    
    /***
     * Splits a string up by spaces. Spaces are ignored when wrapped in quotes.
     *
     * @param command - School Management System cli command
     * @return splits a string by spaces.
     */
    public static List<String> parseArguments(String command) {
        List<String> commandArguments = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find()) commandArguments.add(m.group(1).replace("\"", ""));
        return commandArguments;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the School Management System");
        System.out.println("-".repeat(80));

        Scanner scan = new Scanner(System.in);
        String command = "";

        do {
            System.out.print("Command: ");
            command = scan.nextLine();
            ;
            List<String> commandArguments = parseArguments(command);
            command = commandArguments.get(0);
            commandArguments.remove(0);

            if (command.equals("help")) {
                System.out.println("-".repeat(38) + "Help" + "-".repeat(38));
                System.out.println("test connection \n\tTests the database connection");

                System.out.println("list students \n\tlists all the students");
                System.out.println("list classes \n\tlists all the classes");
                System.out.println("list class_sections \n\tlists all the class_sections");
                System.out.println("list class_registrations \n\tlists all the class_registrations");
                System.out.println("list instructor <first_name> <last_name>\n\tlists all the classes taught by that instructor");


                System.out.println("delete student <studentId> \n\tdeletes the student");
                System.out.println("create student <first_name> <last_name> <birthdate> \n\tcreates a student");
                System.out.println("register student <student_id> <class_section_id>\n\tregisters the student to the class section");

                System.out.println("submit grade <studentId> <class_section_id> <letter_grade> \n\tcreates a student");
                System.out.println("help \n\tlists help information");
                System.out.println("quit \n\tExits the program");
            } else if (command.equals("test") && commandArguments.get(0).equals("connection")) {
                Database.testConnection();
            } else if (command.equals("list")) {
                if (commandArguments.get(0).equals("students")) listAllStudents();
                if (commandArguments.get(0).equals("classes")) listAllClasses();
                if (commandArguments.get(0).equals("class_sections")) listAllClassSections();
                if (commandArguments.get(0).equals("class_registrations")) listAllClassRegistrations();

                if (commandArguments.get(0).equals("instructor")) {
                    getAllClassesByInstructor(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("create")) {
                if (commandArguments.get(0).equals("student")) {
                    createNewStudent(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("register")) {
                if (commandArguments.get(0).equals("student")) {
                    registerStudent(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("submit")) {
                if (commandArguments.get(0).equals("grade")) {
                    submitGrade(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("delete")) {
                if (commandArguments.get(0).equals("student")) {
                    deleteStudent(commandArguments.get(1));
                }
            } else if (!(command.equals("quit") || command.equals("exit"))) {
                System.out.println(command);
                System.out.println("Command not found. Enter 'help' for list of commands");
            }
            System.out.println("-".repeat(80));
        } while (!(command.equals("quit") || command.equals("exit")));
        System.out.println("Bye!");
    }
}

