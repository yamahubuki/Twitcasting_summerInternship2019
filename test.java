class Test {

	public static void main(String[] args){
		for(int i=0;i<4096;i++){	//4^6=4096‰ñ
			for(int j=0;j<6;j++){	//ŒvŽZ‚Í‚U‰ñB
				switch (i%(int)Math.pow(4,j+1)/(int)Math.pow(4,j)){
					case 0:
						System.out.print("+");
						break;
					case 1:
						System.out.print("-");
						break;
					case 2:
						System.out.print("*");
						break;
					case 3:
						System.out.print("/");
						break;
				}
			}
			System.out.println();
		}
	}
}
