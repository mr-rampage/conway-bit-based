package ca.wbac.conway;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LifeTest {

    Life fixture;

    @Before
    public void setUp() {
        fixture = new Life(0b011100000000000, 5, 5);
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
    public void shouldCountBitsInAnInt() {
        assertThat(fixture.countBits(0b111), is(3));
    }

    @Test
    public void shouldReturnTrueIfAlive() {
    	assertThat(fixture.bitAt(1, 0), is(1));
    	assertThat(fixture.bitAt(0, 1), is(0));
    }
    
    @Test
    public void shouldCountNeighbours() {
    	assertThat(fixture.countNeighbours(0b1011, 0, 3, 9), is(2));
    	assertThat(fixture.countNeighbours(0b1011, 2, 3, 9), is(1));
    	assertThat(fixture.countNeighbours(0, 8, 3, 9), is(0));
    }
    
    @Test
    public void shouldOscillate() {
    	int blinkerP1 = 0b010010010;
    	int blinkerP2 = 0b111000;
    	assertThat(fixture.next(blinkerP1, 3, 3), is(blinkerP2));
    	assertThat(fixture.next(blinkerP2, 3, 3), is(blinkerP1));
    }
    
    @Test
    public void shouldBeFixed() {
    	int beehive = 0b1100010010001100000000;
    	int block = 0b11001100000;
    	assertThat(fixture.next(beehive, 6, 5), is(beehive));
    	assertThat(fixture.next(block, 4, 4), is(block));
    }
    
    @Test
    public void shouldRun() {
    	fixture.run(10);
    }
    
    @Test
    public void shouldMaskAreaByIndex() {
    	assertThat(fixture.maskArea(0, 3, 9), is(0b011011));
    	assertThat(fixture.maskArea(4, 3, 9), is(0b111111111));
    	assertThat(fixture.maskArea(0, 3, 9), is(0b11011));
        assertThat(fixture.maskArea(6, 4, 16), is(0b111011101110));
        assertThat(fixture.maskArea(10, 4, 16), is(0b1110111011100000));
        assertThat(fixture.maskArea(8, 4, 16), is(0b11001100110000));
        assertThat(fixture.maskArea(2, 4, 16), is(0b11101110));
        assertThat(fixture.maskArea(2, 3, 9), is(0b110110));
    }
}
