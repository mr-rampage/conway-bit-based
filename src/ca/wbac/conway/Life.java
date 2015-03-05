package ca.wbac.conway;

import java.util.BitSet;

public class Life {
    private int height;
    private int width;
    private BitSet world;
    
    boolean dies(int p) {
        return (p < 2 || p > 3);
    }

    boolean grow(int p) {
        return p == 3;
    }
    
    BitSet maskArea(int index, int width, int height) {
    	BitSet mask = new BitSet(width * height);
    	int x = index % width;
    	int y = Math.floorDiv(index, width);
    	int bits = 9;

    	while(bits-- > 0) {
    		int dx = x - 1 + bits % 3;
    		int dy = y - 1 + Math.floorDiv(bits, 3);
    		if (dx >= 0 && dx < width && dy >= 0 && dy < height) {
    			mask.set(dx + dy * width);
    		}
    	}
    	
    	return mask;
    }
    
    int countNeighbours(BitSet world, int index, int width, int height) {
    	BitSet buffer = (BitSet) world.clone();
    	BitSet mask = maskArea(index, width, height);
    	buffer.and(mask);
    	return buffer.cardinality() - (buffer.get(index) ? 1 : 0);
    }
    
    BitSet next(BitSet world, int width, int height) {
    	BitSet newWorld = (BitSet) world.clone();
    	int index = 0;
    	int size = width * height;
    	while (index < size) {
			int population = countNeighbours(world, index, width, height);
			if (dies(population)) newWorld.set(index, false);
			if (grow(population)) newWorld.set(index, true);
			index++;
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
