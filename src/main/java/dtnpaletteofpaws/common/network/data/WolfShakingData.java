package dtnpaletteofpaws.common.network.data;

public class WolfShakingData {
    
    public int dogId;
    public State state;
    public WolfShakingData(int dogId, State state) {
        this.dogId = dogId;
        this.state = state;
    }

    public static enum State {
        SHAKE_WATER(0),
        SHAKE_LAVA(1),
        STOP(2);

        private final int id;
        private State(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static State fromId(int i) {
            var values = State.values();
            return values[i];
        }

    }

}
