package com.techelevator.tenmo;

import com.techelevator.tenmo.exceptions.InvalidChoiceException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import com.techelevator.tenmo.exceptions.UserNotFoundException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;
import io.cucumber.java.bs.A;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountService accountService;
	private UserService userService;
	private TransferService transferService;
	private TransferTypeService transferTypeService;
	private TransferStatusService transferStatusService;


	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = new RestAccountService(API_BASE_URL);
		this.userService = new RestUserService(API_BASE_URL);
		this.transferService = new RestTransferService(API_BASE_URL);
		this.transferTypeService = new RestTransferTypeService(API_BASE_URL);
		this.transferStatusService = new RestTransferStatusService(API_BASE_URL);
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		BigDecimal balance = accountService.getBalance(currentUser);
		System.out.println("");
		System.out.println("Your current account balance is:  $" + balance);
	}

	private void viewTransferHistory() {
		Transfer[] transfers = transferService.getTransfersByUserId(currentUser, currentUser.getUser().getId());
		printAllTransfers(currentUser, transfers);

		int transferIdSelection = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel): ");
		if (validateTransferChoice(transferIdSelection, transfers)) {
			printTransferDetails(currentUser, transferIdSelection);
		}

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void sendBucks() {
		// TODO Auto-generated method stub
		User[] users = userService.getAllUsers(currentUser);
		printUsersInfo(currentUser, users);

		int userIdSelection = console.getUserInputInteger("Enter ID of user to send to (enter 0 to cancel)");
		if (validateUserChoice(userIdSelection, users, currentUser)) {
			String amount = console.getUserInput("Enter amount to send: ");
			createTransfer(userIdSelection, amount, "Send", "Approved");
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch(AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	private void printUsersInfo(AuthenticatedUser authenticatedUser, User[] users) {

		System.out.println("-------------------------------");
		System.out.println("Users");
		System.out.println("ID          Name");
		System.out.println("-------------------------------");

		console.printUsers(users);
	}

	private void printAllTransfers(AuthenticatedUser authenticatedUser, Transfer[] transfers) {
		List<String> results = new ArrayList<>();

		for (Transfer transfer : transfers) {
			results.add(transfer.getTransferId() + "     " +  toOrFromLogic(currentUser, transfer)   + "     $ " + transfer.getAmount());
		}

		System.out.println("-----------------------------------");
		System.out.println("Transfers");
		System.out.println("ID         From/To          Amount");
		System.out.println("-----------------------------------");

		for (String line : results) {
			System.out.println(line);
		}

		System.out.println("---------");
	}

	private String toOrFromLogic(AuthenticatedUser authenticatedUser, Transfer transfer) {
		String toOrFrom;
		int accountTo = transfer.getAccountTo();
		int accountFrom = transfer.getAccountFrom();

		if (accountService.getAccountById(currentUser, accountTo).getUserId() == authenticatedUser.getUser().getId()) {
			int accountFromUserId = accountService.getAccountById(authenticatedUser,accountFrom).getUserId();
			String usernameFrom = userService.getUserByUserId(authenticatedUser, accountFromUserId).getUsername();
			toOrFrom = "From: " + usernameFrom;
		} else {
			int accountToUserId = accountService.getAccountById(authenticatedUser, accountTo).getUserId();
			String usernameTo = userService.getUserByUserId(currentUser, accountToUserId).getUsername();
			toOrFrom = "To:   " + usernameTo;
		}
		return toOrFrom;
	}

	private boolean validateUserChoice(int userIdChoice, User[] users, AuthenticatedUser currentUser) {
		if(userIdChoice != 0) {
			try {
				boolean validUserIdChoice = false;

				for (User user : users) {
					if(userIdChoice == currentUser.getUser().getId()) {
						throw new InvalidChoiceException();
					}
					if (user.getId() == userIdChoice) {
						validUserIdChoice = true;
						break;
					}
				}
				if (validUserIdChoice == false) {
					throw new UserNotFoundException();
				}
				return true;
			} catch (UserNotFoundException | InvalidChoiceException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	private Transfer createTransfer (int accountChoiceUserId, String amountString, String transferType, String status){

		int transferTypeId = transferTypeService.getTransferTypeFromDescription(currentUser, transferType).getTransferTypeId();
		int transferStatusId = transferStatusService.getTransferStatusByDesc(currentUser, status).getTransferStatusId();
		int accountToId;
		int accountFromId;


		if(transferType.equals("Send")) {
			accountToId = accountService.getAccountByUserId(currentUser, accountChoiceUserId).getAccountId();
			accountFromId = accountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
		} else {
			accountToId = accountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
			accountFromId = accountService.getAccountByUserId(currentUser, accountChoiceUserId).getAccountId();
		}

		BigDecimal amount = new BigDecimal(amountString);

		Transfer transfer = new Transfer();
		transfer.setAccountFrom(accountFromId);
		transfer.setAccountTo(accountToId);
		transfer.setAmount(amount);
		transfer.setTransferStatusId(transferStatusId);
		transfer.setTransferTypeId(transferTypeId);

		transferService.createTransfer(currentUser, transfer);

		return transfer;
	}


	private boolean validateTransferChoice(int transferIdChoice, Transfer[] transfers) {
		if(transferIdChoice != 0) {
			try {
				boolean validateTransferChoice = false;

				for (Transfer transfer : transfers) {
					if (transfer.getTransferId() == transferIdChoice) {
						validateTransferChoice = true;
						break;
					}
				}
				if (validateTransferChoice == false) {
					throw new TransferNotFoundException();
				}
				return true;
			} catch (TransferNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	private void printTransferDetails(AuthenticatedUser authenticatedUser, int transferIdNumber) {
		Transfer transfer = transferService.getTransferByTransferId(currentUser, transferIdNumber);
		TransferType transferType = transferTypeService.getTransferTypeByTransferTypeId(currentUser, transfer.getTransferTypeId());
		TransferStatus transferStatus = transferStatusService.getTransferStatusById(currentUser, transfer.getTransferStatusId());


		Account accountFrom = accountService.getAccountById(currentUser, transfer.getAccountFrom());
		User userFrom = userService.getUserByUserId(currentUser, accountFrom.getUserId());

		Account accountTo = accountService.getAccountById(currentUser, transfer.getAccountTo());
		User userTo = userService.getUserByUserId(currentUser, accountTo.getUserId());

		System.out.println("--------------------------------------------");
		System.out.println("Transfer Details");
		System.out.println("--------------------------------------------");
		System.out.println("Id:     " + transfer.getTransferId());
		System.out.println("From:   " + userFrom.getUsername());
		System.out.println("To:     " + userTo.getUsername());
		System.out.println("Type:   " + transferType.getTransferTypeDesc());
		System.out.println("Status: " + transferStatus.getTransferStatusDesc());
		System.out.println("Amount: " + transfer.getAmount());
	}
}
