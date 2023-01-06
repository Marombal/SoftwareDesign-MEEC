import Controllers.registerMenuController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class registerMenuControllerTest {

    //UNIT TESTS FOR CHECK_PASSWORD

    //Unit test of check_password, testing when the field is empty
    @Test
    void check_password_empty_field(){
        registerMenuController register = new registerMenuController();
        assertEquals(-3,register.check_password("",""));
    }

    //Unit test of check_password, testing when password and confirm password doesn't match
    @Test
    void check_password_no_match(){
        registerMenuController register = new registerMenuController();
        assertEquals(-2,register.check_password("password","nomatch"));
    }

    //Unit test of check_password, testing when password doesn't respect minimum parameters (too weak password)
    @Test
    void check_password_respect_parameters(){
        registerMenuController register = new registerMenuController();
        assertEquals(-1,register.check_password("password","password"));
    }

    //Unit test of check_password, testing when everything ok
    @Test
    void check_password_all_ok(){
        registerMenuController register = new registerMenuController();
        assertEquals(1,register.check_password("Password1","Password1"));
    }

    //UNIT TESTS FOR CHECK_USERNAME

    //Unit test of check_username, testing when the field is empty
    @Test
    void check_user_empty_field(){
        registerMenuController register = new registerMenuController();
        assertEquals(-3,register.check_username(""));
    }

    //Unit test of check_username, testing when user already exists
    //in this test the user Testmail1 like other users is a default user important for test and debugging
    // (important in this unit test because is used to test if the user already exists)
    //for this Unit Test to work correctly the user needs to have the FEUP_VPN on because the database used needs that
    @Test
    void check_user_already_used(){
        registerMenuController register = new registerMenuController();
        assertEquals(-2,register.check_username("Testmail1"));
    }

    //Unit test of check_username, testing when username doesn't respect minimum parameters (too weak password)
    @Test
    void check_user_respect_parameters(){
        registerMenuController register = new registerMenuController();
        assertEquals(-1,register.check_username("user"));
    }

    //Unit test of check_username, testing when everything ok
    //for this Unit Test to work correctly the user needs to have the FEUP_VPN on because the database used needs that
    @Test
    void check_user_all_ok(){
        registerMenuController register = new registerMenuController();
        assertEquals(1,register.check_username("Username1"));
    }

    //UNIT TESTS FOR CHECK_EMAIL

    //Unit test of check_email, testing when the field is empty
    @Test
    void check_mail_empty_field(){
        registerMenuController register = new registerMenuController();
        assertEquals(-3,register.check_email("",""));
    }

    //Unit test of check_email, testing when email and confirm email doesn't match
    @Test
    void check_mail_no_match(){
        registerMenuController register = new registerMenuController();
        assertEquals(-2,register.check_email("email","nomatch"));
    }

    //Unit test of check_password, testing when email isn't a valid email
    @Test
    void check_mail_respect_parameters(){
        registerMenuController register = new registerMenuController();
        assertEquals(-1,register.check_email("noemail","noemail"));
    }

    //Unit test of check_password, testing when everything ok
    @Test
    void check_mail_all_ok(){
        registerMenuController register = new registerMenuController();
        assertEquals(1,register.check_email("email@gmail.com","email@gmail.com"));
    }

}