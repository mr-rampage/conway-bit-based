package ca.wbac.conway;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

public class LifeTest {

    Life fixture;

    @Before
    public void setUp() {
        fixture = new Life(new int[]{7, 12, 17}, 5, 5);
    }
    
    @Test
    public void shouldDieIfUnderOrOverPopulated() {
        assertThat(fixture.dies(1), is(true));
        assertThat(fixture.dies(5), is(true));
    }

    @Test
    public void shouldGrowIfPopulationIsThree() {
        assertThat(fixture.grow(3), is(true));
        assertThat(fixture.grow(1), is(false));
    }
    
    @Test 
    public void shouldMaskAreaByIndex() {
    	BitSet world = new BitSet(9);
    	world.set(0, 9, true);
    	assertThat(fixture.maskArea(4, 3, 3), is(equalTo(world)));
    	
    	world.clear();
    	world.set(0, 2, true);
    	world.set(3, 5, true);
    	assertThat(fixture.maskArea(0, 3, 3), is(equalTo(world)));
    	
    	world.clear();
    	world.set(0, 2, true);
    	world.set(3, 5, true);
    	world.set(6, 8, true);
    	assertThat(fixture.maskArea(3, 3, 3), is(equalTo(world)));
    	
    	world.clear();
    	world.set(1, 3, true);
    	world.set(4, 6, true);
    	world.set(7, 9, true);
    	assertThat(fixture.maskArea(5, 3, 3), is(equalTo(world)));
    	
    	world.clear();
    	world.set(0, 3, true);
    	world.set(3, 6, true);
    	assertThat(fixture.maskArea(1, 3, 3), is(equalTo(world)));
    	
    	world.clear();
    	world.set(3, 6, true);
    	world.set(6, 9, true);
    	assertThat(fixture.maskArea(7, 3, 3), is(equalTo(world)));
    }
    
    @Test
    public void shouldCountNeighbours() {
    	BitSet world = new BitSet(9);
    	world.set(0);
    	world.set(1);
    	world.set(3);
    	
    	assertThat(fixture.countNeighbours(world, 0, 3, 3), is(2));
    	assertThat(fixture.countNeighbours(world, 2, 3, 3), is(1));
    }
    
    @Test
    public void shouldOscillate() {
    	BitSet blinkerP1 = new BitSet(9);
    	blinkerP1.set(1);
    	blinkerP1.set(4);
    	blinkerP1.set(7);
    	
    	BitSet blinkerP2 = new BitSet(9);
    	blinkerP2.set(3, true);
    	blinkerP2.set(4, true);
    	blinkerP2.set(5, true);
    	
    	assertThat(fixture.next(blinkerP1, 3, 3), is(blinkerP2));
    	assertThat(fixture.next(blinkerP2, 3, 3), is(blinkerP1));
    }
    
    @Test
    public void shouldOscillateOnToad() {
    	BitSet toad = new BitSet(36);
    	toad.set(15, 18, true);
    	toad.set(20, 23, true);
    	
    	assertThat(fixture.next(toad, 6, 6), is(not(toad)));
    	assertThat(fixture.next(fixture.next(toad, 6, 6), 6, 6), is(toad));
    }
    
    @Test
    public void shouldBeFixed() {
    	BitSet block = new BitSet();
    	block.set(0, 2, true);
    	block.set(4, 6, true);
    	
    	assertThat(fixture.next(block, 4, 4), is(block));
    }
}
