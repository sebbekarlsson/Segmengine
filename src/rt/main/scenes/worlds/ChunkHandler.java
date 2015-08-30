package rt.main.scenes.worlds;

public class ChunkHandler implements Runnable {

	public Chunk[][] chunks;
	public Thread thread = new Thread(this);

	public ChunkHandler(Chunk[][] chunks){
		this.chunks = chunks;
	}

	public void start(){
		thread.start();
	}

	@Override
	public void run() {
		while(true){
			for(int x = 0; x < chunks.length; x++){
				for(int z = 0; z < chunks[x].length; z++){
					Chunk chunk = chunks[x][z];

					if(chunk.isLoaded()){
						if(!chunk.hasChunkLoader() && !chunk.hasPlayer()){
							chunk.unload();
							
							System.gc();
						}
					}
				}
			}
		}
	}

}
