package database;

public class loginmassage {
    public int id;
    public String phone;
    public String password;
    public boolean remember=false;

    public loginmassage()
    {

    }

    public loginmassage(int id,String phone,String password,boolean remember)
    {
        this.phone=phone;
        this.password=password;
        this.remember=remember;
    }

    @Override
    public String toString() {
        return "loginmassage{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
