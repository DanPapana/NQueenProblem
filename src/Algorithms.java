public class Algorithms {

    private int[] queen_positions;
    private int size;

    public Algorithms(int[] queen_positions, int size) {
        this.size = size;
        this.queen_positions = queen_positions;
    }

    public int getHeuristic(int[] queenArray) {
        //checking if they attack each other vertically or diagonally and calculating the heuristic value
        int heuristic = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if ((queenArray[i] == queenArray[j]) ||                                                    //vertically
                        (queenArray[i] - i == queenArray[j] - j) ||                                        //diagonally
                        (queenArray[i] + i == queenArray[j] + j)) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }


    //Hill climbing implementation
    int[] climbHill(int iterations) {

        int min_heuristic = getHeuristic(queen_positions);
        boolean found;
        int[] best_positions = new int[size];
        int[][] moves = new int[size][size];
        int k = 0;

        while (getHeuristic(queen_positions) != 0 && k < iterations) {
            k++;
            for (int i = 0; i < size; i++) {
                found = false;
                for (int z = 0; z < size; z++) {
                    int j = (int) (Math.random() * size);

                    if (queen_positions[i] != j) {
                        int[] temp_board = queen_positions;
                        temp_board[i] = j;
                        moves[i][j] = getHeuristic(temp_board);

                        if (moves[i][j] <= min_heuristic) {
                            min_heuristic = moves[i][j];
                            best_positions[i] = j;
                            found = true;
                        }
                    }
                }
                if (found) {
                    queen_positions[i] = best_positions[i];
                    if (getHeuristic(queen_positions) == 0) {
                        break;
                    }
                }
            }
        }
        return queen_positions;
    }


    //Simulated Annealing implementation
    int[] simulateAnnealing(int temperature, float cooling_factor) {

        int min_heuristic = getHeuristic(queen_positions);
        boolean found;
        int[] best_positions = new int[size];
        int[][] moves = new int[size][size];
        int heuristic_change;

        while (getHeuristic(queen_positions) != 0 && temperature > 0) {

            for (int i = 0; i < size; i++) {
                found = false;
                for (int k = 0; k < size; k++) {
                    int j = (int) (Math.random() * size);

                    if (queen_positions[i] != j) {
                        int[] temp_board = queen_positions;
                        temp_board[i] = j;
                        moves[i][j] = getHeuristic(temp_board);

                        heuristic_change = moves[i][j] - min_heuristic;

                        if (heuristic_change < 0) {
                            best_positions[i] = j;
                            found = true;
                        } else if (Math.exp(-heuristic_change / temperature) > Math.round(Math.random())) {
                            best_positions[i] = j;
                            found = true;
                        }
                    }
                    if (found) {
                        queen_positions[i] = best_positions[i];
                        min_heuristic = getHeuristic(queen_positions);
                    }
                }
            }
            temperature -= cooling_factor;
        }
        return queen_positions;
    }


    //Local beam search
    int[] beam(int iterations, int states_number) {

        int[][] arrayP = new int[states_number][];
        int[] temp;// = new int[size];
        int[] fitness = new int[states_number];

        for (int j = 0; j < states_number; j++) {
            /*
            for (int i = 0; i < size; i++) {
                temp[i] = (int) (Math.random() * size);
            }
            arrayP[j] = temp;
          */
            arrayP[j] = queen_positions;
        }

        while (getHeuristic(queen_positions) != 0 && iterations > 0) {
            iterations--;
            int[][] arrayQ = new int[size * states_number][];

            for (int i = 0; i < states_number; i++) {
                int min_heuristic = getHeuristic(arrayP[i]);
                if (min_heuristic == 0) {
                    return arrayP[i];
                }

                for (int k = 0; k < size; k++) {
                    for (int m = 0; m < size; m++) {
                        if (m != arrayP[i][k]) {

                            int temporary = arrayP[i][k];
                            arrayP[i][k] = m;

                            int heuristic = getHeuristic(arrayP[i]);
                            if (min_heuristic > heuristic) {
                                arrayP[i][k] = m;
                                arrayQ[i * size + k] = arrayP[i];
                                break;
                            }
                            arrayP[i][k] = temporary;
                        }
                    }

                    if (arrayQ[i * size + k] == null) {
                        arrayQ[i * size + k] = queen_positions;
                    }
                }
            }

            for (int i = 0; i < states_number; i++) {
                fitness[i] = getHeuristic(arrayQ[i]);
            }

            for (int i = 0; i < states_number - 1; i++) {
                for (int j = 0; j < states_number - i - 1; j++) {
                    if (fitness[j] > fitness[j + 1]) {

                        int storer = fitness[j];
                        fitness[j] = fitness[j + 1];
                        fitness[j + 1] = storer;

                        temp = arrayQ[j];
                        arrayQ[j] = arrayQ[j + 1];
                        arrayQ[j + 1] = temp;
                    }
                }
            }
            arrayP = arrayQ;
        }
        return queen_positions;
    }
}
