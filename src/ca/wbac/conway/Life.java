package ca.wbac.conway;

public class Life {
    private final int height;
    private final int width;
    private final int world;

    boolean dies(final int p) {
        return (p < 2 || p > 3);
    }

    boolean grow(final int p) {
        return p == 3;
    }
    
    int maskArea(final int index, final int w, final int size) {
    	int mask = 0;
    	for (int i = -1; i <= 1; i++) {
    		int pos = index + i * w;
    		int min = Math.floorDiv(pos--, w) * w;
    		int max = min + w;
    		if (max > 0) {
	    		for (int j = 0; j <= 2; j++) {
	    			int cell = pos + j;
		    		if (cell >= 0 && cell < size && 
		    				cell >= min && cell < max ) {
		    	    	mask ^= 1 << cell;
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
        	count += buffer & 1;
            buffer >>= 1;
        }
        return count;
    }
    
    int bitAt(final int world, final int index) {
    	return (world & 1 << index) >> index;
    }
    
    int countNeighbours(final int world, final int index, final int w, final int s) {
    	int mask = maskArea(index, w, s);
    	return countBits(world & mask) - bitAt(world, index);
    }
    
    int next(final int world, final int w, final int h) {
    	int newWorld = world;
    	int index = 0;
    	int size = w * h;
    	while (size > 0 && index < size) {
			int population = countNeighbours(world, index, w, size);
			if (bitAt(world, index) == 1) {
				if (dies(population)) {
					newWorld ^= 1 << index;
				}
			} else if (grow(population)) {
				newWorld ^= 1 << index;
			}
			index++;
    	}
    	return newWorld;
    }
    
    public void run(final int generations) {
    	int g = generations;
    	int w = this.world;
    	while (g-- > 0) {
    		w = this.next(w, this.width, this.height);
    	}
    }

    public Life(final int seed, final int columns, final int rows) {
        this.width = columns;
        this.height = rows;
        this.world = seed;
    }
}
