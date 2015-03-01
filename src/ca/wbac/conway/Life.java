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
    	int x = index % w;
    	int y = Math.floorDiv(index, w);
    	int pattern = ((x == 0 || x == (w - 1))? 3 : 7);
    	int crop = (1 << size) - 1;
    	int offsetX = (x > 0 ? x - 1 : 0);
    	int offsetY = (y > 1 ? (y - 1) * w :  0);
    	int mask = (pattern << w | pattern) << offsetX;
    	if (y > 0) {
    		mask = ((mask << w ) | (pattern << offsetX)) << offsetY;
    	}
    	return (mask & crop);
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
			if (dies(population)) {
				newWorld &= ~(1 << index);
			}
			if (grow(population)) {
				newWorld |= 1 << index;
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
