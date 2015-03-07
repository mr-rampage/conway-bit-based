package ca.wbac.conway;

import java.util.BitSet;

public class Life {
    private int height;
    private int width;
    private BitSet world;
    
    boolean isAlive(int p, boolean isAlive) {
    	return p == 3 || p == 2 && isAlive;
    }
    
    int countNeighbours(BitSet world, int index, int width, int height) {
    	int x = index % width;
    	int y = Math.floorDiv(index, width);
    	int area = 9;
    	int count = 0;

    	while(area-- > 0) {
    		int dx = x - 1 + area % 3;
    		int dy = y - 1 + Math.floorDiv(area, 3);
    		int i = dx + dy * width;
    		if (dx >= 0 && dx < width 
    				&& dy >= 0 && dy < height 
    				&& i != index && world.get(i)) {
				count++;
    		}
    	}
    	return count;
    }
    
    BitSet next(BitSet world, int width, int height) {
    	BitSet newWorld = (BitSet) world.clone();
    	int index = width * height;
    	while (index-- > 0) {
			int population = countNeighbours(world, index, width, height);
			newWorld.set(index, isAlive(population, newWorld.get(index)));
    	}
    	return newWorld;
    }
    
    public void render(BitSet world, int length) {
    	int n = 0;
    	while (length-- > 0) {
    		System.out.print(world.get(n) ? "1" : "0");
			if (++n % width == 0) System.out.println();
    	}
    	System.out.println();
    }
    
    public void run(int generations) {
    	int g = generations;
    	while (g-- > 0) {
    		render(this.world, this.width * this.height);
    		this.world = this.next(this.world, this.width, this.height);
    	}
    }

    public Life(int[] seed, int columns, int rows) {
        this.width = columns;
        this.height = rows;
        this.world = new BitSet(columns * rows);
        for (int i : seed) {
        	this.world.set(i);
        }
    }
}
