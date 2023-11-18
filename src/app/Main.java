package app;

import data_access.FileUserDataAccessObject;
import data_access.FileFavouritesDataAccessObject;
import entity.RestaurantFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.register.RegisterViewModel;
import interface_adapter.view_favourites.ViewFavouritesViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Restaurant Search Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);


        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        RegisterViewModel registerViewModel = new RegisterViewModel();
        ViewFavouritesViewModel viewFavouritesViewModel = new ViewFavouritesViewModel();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new UserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileFavouritesDataAccessObject fileFavouritesDataAccessObject;
        try {
            fileFavouritesDataAccessObject = new FileFavouritesDataAccessObject("./favourites.csv", new RestaurantFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RegisterView registerView = RegisterUseCaseFactory.create(viewManagerModel, loginViewModel, registerViewModel,
                userDataAccessObject);
        views.add(registerView, registerView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, userDataAccessObject);
        views.add(loginView, loginView.viewName);

        LoggedInView loggedInView = ViewFavouritesUseCaseFactory.create(viewManagerModel, loggedInViewModel,
                viewFavouritesViewModel, fileFavouritesDataAccessObject, userDataAccessObject);
        views.add(loggedInView, loggedInView.viewName);

        ViewFavouritesView viewFavouritesView = new ViewFavouritesView(viewFavouritesViewModel,
                LoginUseCaseFactory.createLoginUseCase(viewManagerModel, loginViewModel, loggedInViewModel, userDataAccessObject));
        views.add(viewFavouritesView, viewFavouritesView.viewName);

        viewManagerModel.setActiveView(registerView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}