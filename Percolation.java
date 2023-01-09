import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    //private Integer[][] matrix;
    private Integer[] links;
    private Integer[] weight;
    private Integer openSites;
    private Integer size;
    //private Integer root;

    public Percolation(int n){
        if(n<=0) throw new IllegalArgumentException();
        this.openSites = 0;
        this.size = n;
        this.links = new Integer[(n*n)+2];
        this.weight = new Integer[(n*n)+2];
        for(int i=0; i<(n*n)+2; i++){
            this.links[i] = -1;
        }
        for(int i=0; i<(n*n)+2; i++){
            this.weight[i] = 1;
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        Integer index = row*this.size + col + 1;
        if(this.links[index]==-1){
            this.openSites++;
            this.links[index] = index;
            Integer left, right, down, up;
            if(row==0) union(0,index);
            else if(row==this.size-1) union((this.size*this.size+1), index);
            else{
                if(col!=0){
                    left = index-1;
                    if(links[left]!=-1) union(left,index);
                }
                if(col!=this.size-1){
                    right = index+1;
                    if(links[right]!=-1) union(right,index);
                }
                up = (row-1)*this.size + col + 1;
                down = (row+1)*this.size + col + 1;
                if(links[up]!=-1) union(index,up);
                if(links[down]!=-1) union(down,index);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        Integer index = row*this.size + col + 1;
        return this.links[index]!=-1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        Integer index = row*this.size + col + 1;
        return equal(index,0);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        Integer loweroot = find(this.size*this.size+1);
        Integer upperoot = find(0);
        //if(loweroot==upperoot) this.root = loweroot;
        return loweroot == upperoot;
    }

    private Integer find(Integer a){
        //System.out.println(a);
        if (this.links[a]!=-1){
            while(this.links[a]!=a) a = this.links[a];
            return a;
        }
        return -1;
    }

    private boolean equal(Integer a, Integer b){
        Integer aroot = find(a);
        Integer broot = find(b);
        if(aroot == -1 || broot == -1) return false;
        else return aroot == broot;
    }
    private void union(Integer a, Integer b){
        Integer aroot, broot;
        if(a==0){
            this.links[a]= b;

        }
        if (aroot==-1 || broot == -1) return;
        else if (aroot==broot) return;
        else if(this.weight[aroot]>this.weight[broot]){
            this.links[broot] = aroot;
            this.weight[aroot]+=this.weight[broot];
        }
        else{
            this.links[aroot] = broot;
            this.weight[broot]+=this.weight[aroot];
        }
    }


    // test client (optional)
    public static void main(String[] args){

    }
}