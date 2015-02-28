package ca.wbac.conway;

public class Life {
    private final int height;
    private final int width;
    private final int world;
    
    int getIndex(int x, int y, int w) {
        return x + y * w;
    }

    boolean dies(int p) {
        return (p < 2 || p > 3);
    }

    boolean grow(int p) {
        return p == 3;
    }

    int maskArea(int x, int y, int w) {  	
        int mask = 0;
        for (int dy = -1; dy <= 1; dy++) {
        	if ((y + dy) > -1) {
	        	for (int dx = -1; dx <= 1; dx++) {
	                if ((x + dx) < w && (x + dx) > -1) {
	                    mask |= 1 << getIndex(x + dx, y + dy, w);
	                }
	            }
        	}
        }
        return mask;
    }
    
    int countBits(final int value) {
        int count = 0;
        int buffer = value;
        while (buffer > 0) {
            if ((buffer & 1) == 1) {
                count++;
            }
            buffer >>= 1;
        }
        return count;
    }
    
    int isAlive(int world, int x, int y, int w) {
    	int index = getIndex(x, y, w);
    	return (world & 1 << index) >> index;
    }

    int countNeighbours(int world, int x, int y, int w) {
        int mask = maskArea(x, y, w);
        return countBits(world & mask) - isAlive(world, x, y, w);
    }
    
    int next(int world, int columns, int rows, int width) {
    	int newWorld = world;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
    			int population = countNeighbours(world, x, y, width);
    			int index = getIndex(x, y, width);
    			if (isAlive(world, x, y, width) == 1) {
    				if (dies(population)) {
    					newWorld ^= (1 << index);
    				}
    			} else if (grow(population)) {
					newWorld |= 1 << index;
    			}
    		}
    	}
    	return newWorld;
    }
    
    void render(int world, int columns, int rows, int width) {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
    			int index = getIndex(x, y, width);
    			System.out.print((((world >> index) & 1) == 1 ? "*" : "."));
    		}
			System.out.println();
    	}
		System.out.println();
    }
    
    public void run(final int generations) {
    	int g = generations;
    	int w = this.world;
    	while (g-- > 0) {
    		w = this.next(w, this.width, this.height, this.width);
    		this.render(w, this.width, this.height, this.width);
    	}
    }

    public Life(final int seed, final int columns, final int rows) {
        this.width = columns;
        this.height = rows;
        this.world = seed;
    }
}
