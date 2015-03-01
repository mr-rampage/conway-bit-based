package ca.wbac.conway;

public class Life {
    private  int height;
    private  int width;
    private  int world;

    boolean dies(int p) {
        return (p < 2 || p > 3);
    }

    boolean grow(int p) {
        return p == 3;
    }
    
    int maskArea(int index, int w, int size) {
    	int x = index % w;
    	int y = Math.floorDiv(index, w);
    	int pattern = (x == 0 || x == (w - 1) ? 0b11 : 0b111);
    	int crop = (1 << size) - 1;
    	int offsetX = (x > 0 ? x - 1 : 0);
    	int offsetY = (y > 0 ? (y - 1) * w :  0);
    	int mask = (pattern << w | pattern) << offsetX;
    	if (y > 0) mask = ((mask << w ) | (pattern << offsetX)) << offsetY;
    	return (mask & crop);
    }
    
    int countBits(int value) {
        int count = 0;
        int buffer = value;
        while (buffer > 0) {
        	count += buffer & 1;
            buffer >>= 1;
        }
        return count;
    }
    
    int bitAt(int world, int index) {
    	return (world & 1 << index) >> index;
    }
    
    int countNeighbours(int world, int index, int w, int s) {
    	int mask = maskArea(index, w, s);
    	return countBits(world & mask) - bitAt(world, index);
    }
    
    int next(int world, int w, int h) {
    	int newWorld = world;
    	int index = 0;
    	int size = w * h;
    	while (size > 0 && index < size) {
			int population = countNeighbours(world, index, w, size);
			if (dies(population)) newWorld &= ~(1 << index);
			if (grow(population)) newWorld |= 1 << index;
			index++;
    	}
    	return newWorld;
    }
    
    public void render(int world, int width, int height) {
    	int buffer = world;
    	int n = 0;
    	int s = width * height;
    	while (s-- > 0) {
    		System.out.print(buffer & 1);
    		buffer >>= 1;
			if (++n % width == 0) System.out.println();
    	}
    	System.out.println();
    }
    
    public void run(int generations) {
    	int g = generations;
    	int w = this.world;
    	while (g-- > 0) {
    		render(w, this.width, this.height);
    		w = this.next(w, this.width, this.height);
    	}
    }

    public Life(int seed, int columns, int rows) {
        this.width = columns;
        this.height = rows;
        this.world = seed;
    }
}
