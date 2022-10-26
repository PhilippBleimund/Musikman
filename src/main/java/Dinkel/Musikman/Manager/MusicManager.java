public class MusicManager{

    public static MusicManager INSTANCE;

    public void update(){
        
    }

    public static MusicManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new MusicManager();
		}
		
		return INSTANCE;
	}
}