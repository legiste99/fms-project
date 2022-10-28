package za.ac.cput.fms.model.fixture;

import za.ac.cput.fms.util.Helper;

public class FixtureFactory {
    public static Fixture newFixture(){
        String id = "fx-"+Helper.generateId();

        return new Fixture.Builder()
                .setId(id)
                .setStatus(0)
                .build();

    }
}
