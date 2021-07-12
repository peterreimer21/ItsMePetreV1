package entity;

import tilemap.*;

public class Gegner extends MapObject{
		
	protected int leben;
	protected int maxLeben;
	protected boolean tot;
	protected int schaden;
	
	protected boolean flackern;
	protected long flackernTimer;
		
	public Gegner(TileMap tileMap) {
		super(tileMap);
	}
	
	public boolean istTot() { 
		return tot;
	}
	
	public int getSchaden() { 
		return schaden;
	}
	
	public void treffer(int schaden) {
		if(tot || flackern){
			return;
		}
		leben -= schaden;
		if(leben < 0) leben = 0;
		if(leben == 0) tot = true;
		flackern = true;
		flackernTimer = System.nanoTime();
	}
	
	public void update(){}
}