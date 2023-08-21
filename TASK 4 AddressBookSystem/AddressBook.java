import java.util.*;
import java.io.*;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

public class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void saveContactsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
            }
            System.out.println("Contacts saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public void loadContactsFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    String email = parts[2];
                    Contact contact = new Contact(name, phoneNumber, email);
                    contacts.add(contact);
                }
            }
            System.out.println("Contacts loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact added.");
    }

    public void removeContact(Contact contact) {
        if (contacts.remove(contact)) {
            System.out.println("Contact removed.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        List<Contact> allContacts = new ArrayList<>(); // Initialize the list here

        while (true) {
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. View All Contacts");
            System.out.println("4. Search Contacts");
            System.out.println("5. Update Contact");
            System.out.println("6. Sort Contacts");
            System.out.println("7. Save Contacts to File");
            System.out.println("8. Load Contacts from File");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number: ");
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber, email);
                    addressBook.addContact(newContact);
                    break;
    
        //Code By Shashank Singh Gautam
                
                case 2:
                    System.out.print("Enter the name of the contact to remove: ");
                    String nameToRemove = scanner.nextLine();
                    Contact contactToRemove = null;
                    for (Contact contact : allContacts) {
                        if (contact.getName().equalsIgnoreCase(nameToRemove)) {
                            contactToRemove = contact;
                            break;
                        }
                    }
                    if (contactToRemove != null) {
                        addressBook.removeContact(contactToRemove);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 3:
                    allContacts = addressBook.getAllContacts();
                    for (Contact contact : allContacts) {
                        System.out.println("Name: " + contact.getName());
                        System.out.println("Phone Number: " + contact.getPhoneNumber());
                        System.out.println("Email: " + contact.getEmail());
                        System.out.println("-----------------------");
                    }
                    break;
                case 4:
                    System.out.print("Enter search keyword: ");
                    String searchKeyword = scanner.nextLine();
                    List<Contact> matchingContacts = new ArrayList<>();
                    for (Contact contact : allContacts) {
                        if (contact.getName().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                                contact.getPhoneNumber().contains(searchKeyword) ||
                                contact.getEmail().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            matchingContacts.add(contact);
                        }
                    }
                    if (matchingContacts.isEmpty()) {
                        System.out.println("No matching contacts found.");
                    } else {
                        System.out.println("Matching contacts:");
                        for (Contact contact : matchingContacts) {
                            System.out.println("Name: " + contact.getName());
                            System.out.println("Phone Number: " + contact.getPhoneNumber());
                            System.out.println("Email: " + contact.getEmail());
                            System.out.println("-----------------------");
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter the name of the contact to update: ");
                    String nameToUpdate = scanner.nextLine();
                    Contact contactToUpdate = null;
                    for (Contact contact : allContacts) {
                        if (contact.getName().equalsIgnoreCase(nameToUpdate)) {
                            contactToUpdate = contact;
                            break;
                        }
                    }
                    if (contactToUpdate != null) {
                        System.out.println("Enter new information:");
                        System.out.print("New name: ");
                        String newName = scanner.nextLine();
                        contactToUpdate.setName(newName);
                        System.out.print("New phone number: ");
                        String newPhoneNumber = scanner.nextLine();
                        contactToUpdate.setPhoneNumber(newPhoneNumber);
                        System.out.print("New email: ");
                        String newEmail = scanner.nextLine();
                        contactToUpdate.setEmail(newEmail);
                        System.out.println("Contact updated.");
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 6:
                    System.out.println("1. Sort by Name");
                    System.out.println("2. Sort by Phone Number");
                    System.out.println("3. Sort by Email");
                    System.out.print("Enter sorting option: ");
                    int sortingOption;
                    while (true) {
                        try {
                            sortingOption = Integer.parseInt(scanner.nextLine());
                            if (sortingOption >= 1 && sortingOption <= 3) {
                                break;
                            } else {
                                System.out.println("Invalid sorting option. Enter 1, 2, or 3.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter 1, 2, or 3.");
                        }
                    }
                    Comparator<Contact> comparator = null;
                    switch (sortingOption) {
                        case 1:
                            comparator = Comparator.comparing(Contact::getName);
                            break;
                        case 2:
                        comparator = Comparator.comparing(Contact::getPhoneNumber); 				break;
                        case 3:
      comparator = Comparator.comparing(Contact::getEmail); 
                        break;
  } if (comparator != null) { allContacts.sort(comparator);
   System.out.println("Contacts sorted."); } break; 
  					 case 7:
System.out.print("Enter file name to save contacts: "); String saveFilename = scanner.nextLine(); addressBook.saveContactsToFile(saveFilename);
break; 
						case 8: 
System.out.print("Enter file name to load contacts from: "); String loadFilename = scanner.nextLine(); addressBook.loadContactsFromFile(loadFilename); break;
						case 9: 
System.out.println("Exiting..."); System.exit(0);
						break;
 default: System.out.println("Invalid choice"); } 
 }
	 } 
 }