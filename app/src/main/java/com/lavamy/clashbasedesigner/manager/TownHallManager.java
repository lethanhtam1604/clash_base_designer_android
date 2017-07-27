package com.lavamy.clashbasedesigner.manager;


import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.model.TownHall;

import java.util.ArrayList;

public class TownHallManager {

    private static TownHallManager instance = new TownHallManager();

    public static TownHallManager getInstance() {
        return instance;
    }

    public ArrayList<TownHall> townHalls;

    private TownHallManager() {
        townHalls = new ArrayList<>();
        townHalls.add(new TownHall(R.drawable.town_hall_1, "Town Hall 1", false));
        townHalls.add(new TownHall(R.drawable.town_hall_2, "Town Hall 2", false));
        townHalls.add(new TownHall(R.drawable.town_hall_3, "Town Hall 3", false));
        townHalls.add(new TownHall(R.drawable.town_hall_4, "Town Hall 4", false));
        townHalls.add(new TownHall(R.drawable.town_hall_5, "Town Hall 5", false));
        townHalls.add(new TownHall(R.drawable.town_hall_6, "Town Hall 6", false));
        townHalls.add(new TownHall(R.drawable.town_hall_7, "Town Hall 7", false));
        townHalls.add(new TownHall(R.drawable.town_hall_8, "Town Hall 8", false));
        townHalls.add(new TownHall(R.drawable.town_hall_9, "Town Hall 9", false));
        townHalls.add(new TownHall(R.drawable.town_hall_10, "Town Hall 10", false));
    }

    public void resetData() {
        for (int i = 0; i < townHalls.size(); i++) {
            townHalls.get(i).setSelected(false);
        }
    }
}
