package ma.octo.ksqlmigrations.application;

import ma.octo.ksqlmigrations.startup.Starter;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Starter starter = new Starter();
        starter.run();
    }
}
