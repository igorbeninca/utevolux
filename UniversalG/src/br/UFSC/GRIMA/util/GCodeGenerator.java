package br.UFSC.GRIMA.util;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.*;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.StepNcProject;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class GCodeGenerator {
	
	private Vector <Vector> Workingsteps ;
	private Projeto projeto;
	int lineNumber = 00;
	private double feedRate;
	private double spindleRotation;
	private int rotationDirection;
	public static final int NEUTRAL_HAND_OF_CUT = 0;
	public static final int RIGHT_HAND_OF_CUT = 1;
	public static final int LEFT_HAND_OF_CUT = 2;
	
	public GCodeGenerator(Vector <Vector> Workingsteps, Projeto projeto){
		
		this.Workingsteps = Workingsteps;
		this.projeto = projeto;
	}
	
	public String GenerateGCodeString(){
		
			
		
		
		String GCode = "N" + lineNumber + " G54" + "\n";
		lineNumber = lineNumber + 10;
		
		Feature anterior = null;
		
		for (int index = 0; index < this.Workingsteps.size(); index++)
			
		{
			//System.out.println("Entrou na iteracao int index = 0?");	
			
			Vector workinstepsFaceTmp = this.Workingsteps.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				//System.out.println("Entrou na iteracao int i = 0; antes de verificar qual eh o wsTmp");	
				
				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
				
				double alturaSeguranca = wsTmp.getOperation().getRetractPlane();
				
				//if ((anterior == null) || !(wsTmp.getFeature().equals(anterior))){
				
				if (wsTmp.getFeature().getClass().equals(FuroBasePlana.class) || wsTmp.getFeature().getClass().equals(FuroBaseConica.class))
				{
					Furo furoTmp = (Furo)wsTmp.getFeature();
					int GAux = 0;
					
					if(wsTmp.getCondicoesUsinagem().getF() == 0){this.feedRate = wsTmp.getCondicoesUsinagem().getVf();}
					else{this.feedRate = wsTmp.getCondicoesUsinagem().getF();}
					
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					if (rotationDirection == NEUTRAL_HAND_OF_CUT){GAux = 5;}
					else if (rotationDirection == RIGHT_HAND_OF_CUT){GAux = 3;}
					else if (rotationDirection == LEFT_HAND_OF_CUT){GAux = 4;}
					
					
					
//					if (rotationDirection == 1){GAux = 3;}
//					else if (rotationDirection == 2){GAux = 4;}
//					else if (rotationDirection == 3){GAux = 5;}
										
							
					GCode = GCode + "N" + lineNumber + " S"+ spindleRotation +" F" + feedRate +" M"+GAux + "\n";
					lineNumber = lineNumber + 10;
					double positX = furoTmp.getPosicaoX();
					double positY = furoTmp.getPosicaoY();
					double profundidadeZ = furoTmp.getProfundidade();
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double alturaBloco = projeto.getBloco().getProfundidade();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double planodeReferencia = furoTmp.getPosicaoZ();
					double cuttingDepth = 0;
					Point3d startPoint = wsTmp.getOperation().getStartPoint();
					System.out.println("operation ---->" + wsTmp.getOperation().getId());
					System.out.println("Start Point ---->" + startPoint);
					
					double startZ = -startPoint.getZ();
					
					if (wsTmp.getOperation().getClass().equals(CenterDrilling.class)){
						cuttingDepth = ((CenterDrilling)wsTmp.getOperation()).getCuttingDepth();}
					
					if (wsTmp.getOperation().getClass().equals(Drilling.class)){
						cuttingDepth = ((Drilling)wsTmp.getOperation()).getCuttingDepth();}
					
					if (wsTmp.getOperation().getClass().equals(Boring.class)){
						cuttingDepth = ((Boring)wsTmp.getOperation()).getCuttingDepth();}
					
					if (wsTmp.getOperation().getClass().equals(Reaming.class)){
						cuttingDepth = ((Reaming)wsTmp.getOperation()).getCuttingDepth();}
					
					
					
					GCode = GCode + "N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"" + "\n";
					lineNumber = lineNumber + 10;
					
					GCode = GCode + "N"+lineNumber+ " M6" + "\n" ;
					lineNumber = lineNumber + 10;
										
					GCode = GCode  + "N"+lineNumber+ " G0" + " X"+positX + " Y"+positY + "\n" ;
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode  + "N" + lineNumber + " M8" + "\n" ;
						lineNumber = lineNumber + 10;
						
					}
					
			
					
					if (wsTmp.getOperation().getClass().equals(CenterDrilling.class) || wsTmp.getOperation().getClass().equals(Drilling.class)
							|| wsTmp.getOperation().getClass().equals(Reaming.class) || wsTmp.getOperation().getClass().equals(Boring.class)){
						
						GCode = GCode + "N"+lineNumber+ " CYCLE81("+alturaSeguranca+", "+startZ+", "
					     +alturaSeguranca+ ", "+ (startZ-cuttingDepth)+", " +")" + "\n" ;
						lineNumber = lineNumber + 10;
																						
					}
					
					double sideAllowance = 0;
					double bottomAllowance = 0;
					double Ap = wsTmp.getCondicoesUsinagem().getAp();
					
					
					if (wsTmp.getOperation().getClass().equals(BottomAndSideRoughMilling.class)){
						
						 sideAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceSide();
						 bottomAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceBottom();
						 
							double dirFresamento = 0;
							
							if (rotationDirection == RIGHT_HAND_OF_CUT){dirFresamento = 2;}
							if (rotationDirection == LEFT_HAND_OF_CUT){dirFresamento = 3;}
							
							GCode = GCode + "N" + lineNumber + " POCKET2(" + alturaSeguranca + ", " + startZ +", " 
									 + ", " + (-1 * (profundidadeZ - bottomAllowance)) + ", " 
									 + ", " + (furoTmp.getRaio() - sideAllowance) 
									+ ", " + furoTmp.getPosicaoX() + ", " + furoTmp.getPosicaoY() + ", " 
									+ feedRate + ", "  + (feedRate*2) + ", " + Ap + ", " 
									+ dirFresamento + ", "+ ", " + ", "+ ", " + ", " + ") " + "\n";
							
							lineNumber = lineNumber + 10;
						 						 			
					}
					
					if (wsTmp.getOperation().getClass().equals(BottomAndSideFinishMilling.class)){
						
						double dirFresamento = 0;
										
						if (rotationDirection == RIGHT_HAND_OF_CUT){dirFresamento = 2;}
						if (rotationDirection == LEFT_HAND_OF_CUT){dirFresamento = 3;}
						
						GCode = GCode + "N" + lineNumber + " POCKET2(" + alturaSeguranca + ", " + startZ +", " 
								 + ", " + (-1 * (profundidadeZ)) + ", " 
								 + ", " + furoTmp.getRaio() 
								+ ", " + furoTmp.getPosicaoX() + ", " + furoTmp.getPosicaoY() + ", " 
								+ feedRate + ", "  + (feedRate*2) + ", " + Ap + ", " + dirFresamento + ", "+ ", " + ", "+ ", " + ", " + ") " + "\n";
						
						lineNumber = lineNumber + 10;
																					
					}
									
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode + "N" + lineNumber + " M9" + "\n" ;
						lineNumber = lineNumber + 10;
					}
								
				}
				
			    if (wsTmp.getFeature().getClass().equals(Cavidade.class))
				{
			    	//System.out.println("Verificou que eh cavidade?");
			    	Cavidade cavidadeTmp = (Cavidade)wsTmp.getFeature();
					int GAux = 0;
					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
					
					System.out.println("GAux = " + GAux);
					
					GCode = GCode + "N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n" ;
					lineNumber = lineNumber + 10;
					
					double positX = cavidadeTmp.getPosicaoX();
					double positY = cavidadeTmp.getPosicaoY();
					double comprimentoCavidade = cavidadeTmp.getComprimento();
					double larguraCavidade = cavidadeTmp.getLargura();
					double xCentro = (cavidadeTmp.getPosicaoX()+ (comprimentoCavidade/2));
					double yCentro = (cavidadeTmp.getPosicaoY()+ (larguraCavidade/2));
					double raioCavidade = cavidadeTmp.getRaio();
					double profundidadeZ = cavidadeTmp.getProfundidade()*-1;
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double planoDeReferencia = cavidadeTmp.getPosicaoZ();
					//double alturaBloco = projeto.getBloco().getProfundidade();
					double avancoVertical = wsTmp.getCondicoesUsinagem().getAp();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double velCorte = wsTmp.getCondicoesUsinagem().getVc();
					double tolerancia = wsTmp.getFeature().getTolerancia();
							
					System.out.println("Tolerancia = " + tolerancia);
					
					GCode = GCode + "N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"" + "\n" ;
					lineNumber = lineNumber + 10;
					
					GCode = GCode + "N"+lineNumber+ " M6" + "\n" ;
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode  + "N" + lineNumber + " M8" + "\n";
						lineNumber = lineNumber + 10;
					}
					
					double sideAllowance = 0;
					double bottomAllowance = 0;
					
					if (wsTmp.getOperation().getClass().equals(BottomAndSideRoughMilling.class)){
						
						 sideAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceSide();
						 bottomAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceBottom();
						

						 System.out.println("side Allowance " + sideAllowance);
						 System.out.println("bottom Allowance " + bottomAllowance);
					}
					
				
					
					//if (wsTmp.getFeature().isAcabamento()){
					if (wsTmp.getCondicoesUsinagem().isAcabamento() || (wsTmp.getTipo() == Workingstep.ACABAMENTO)){
						
							double raioFerramenta = wsTmp.getFerramenta().getDiametroFerramenta()/2;
														
							
							DeterminarMovimentacao dm = new DeterminarMovimentacao();
							
						
							   //Acabamento dos Lados
							   
								ArrayList<Path> trajetoriasCavidade = dm.getTrajetoriasAcabamentoLadosCavidade(wsTmp);
								
								
								
								   for(int j = 0; j < trajetoriasCavidade.size(); j++)
								{
									double xAux = trajetoriasCavidade.get(j).getFinalPoint().getX();
									double yAux = trajetoriasCavidade.get(j).getFinalPoint().getY();
									double zAux = trajetoriasCavidade.get(j).getFinalPoint().getZ();
									
									if (trajetoriasCavidade.get(j).getClass().equals(LinearPath.class) ){
										
										int tipoDeMovimento = 0;
										
										if(((LinearPath)trajetoriasCavidade.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
										else if (((LinearPath)trajetoriasCavidade.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}
										
										GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+zAux + "\n";
										
										lineNumber = lineNumber + 10;
									}
									
									if (trajetoriasCavidade.get(j).getClass().equals(CircularPath.class) ){
										
										double rAux = ((CircularPath)trajetoriasCavidade.get(j)).getRadius();
										
										GCode = GCode + "N" + lineNumber + " G3" + " X"+xAux + " Y"+yAux+ " Z"+zAux +
												" CR="+rAux+
										
										
										"\n";
										
										lineNumber = lineNumber + 10;
									}
																
								}
							   
								 
//								Aqui completa com pocket na base
								
								double profundidadePocketAcabamento = bottomAllowance;
								double referenciaPocketAcabamento = profundidadeZ - bottomAllowance;
								double comprimentoPocketAcabamento = comprimentoCavidade - (2*sideAllowance);
								double larguraPocketAcabamento = larguraCavidade - (2*sideAllowance);
								double raioPocketAcabamento = raioCavidade - sideAllowance;
								double startZfinish = wsTmp.getOperation().getStartPoint().getZ();
								
//								GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca+","+(startZfinish*-1)+
//							     ","+alturaSeguranca + ","+ profundidadeZ+ ","
//							     +","+comprimentoPocketAcabamento + ","+ larguraPocketAcabamento + ","+ 
//							     raioPocketAcabamento+ "," + xCentro +","+ yCentro +","+ "0" + ","+
//							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
//							     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";
								
								GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca+","+(((startZfinish*-1)) + profundidadeZ + wsTmp.getFeature().LIMITE_DESBASTE ) +
							     ","+alturaSeguranca + ","+((startZfinish*-1) + profundidadeZ)+ ","
							     +","+comprimentoPocketAcabamento + ","+ larguraPocketAcabamento + ","+ 
							     raioPocketAcabamento+ "," + xCentro +","+ yCentro +","+ "0" + ","+
							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
							     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";
					
								lineNumber = lineNumber + 10;
						
							
						
															
					}
					
					else{
						//System.out.println("Operacao de desbaste ");
						double raioFerramenta = wsTmp.getFerramenta().getDiametroFerramenta()/2;
						
						double raioMaiorDesbaste = 0;
						
						System.out.println("size ws " + wsTmp.getFeature().getWorkingsteps().size());
						for(int j = 0; j < wsTmp.getFeature().getWorkingsteps().size(); j++){
							
							if(((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getTipo() == Workingstep.DESBASTE){
								//System.out.println("j = " + j);
								//System.out.println("raio ws Desbaste " + j + " = " + ((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2);
								if (((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2 > raioMaiorDesbaste){
									raioMaiorDesbaste = ((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2;
								}
							}
							System.out.println("Raio maior de desbaste" + raioMaiorDesbaste);
							
						}
						
						if (raioCavidade < raioFerramenta || raioFerramenta == raioMaiorDesbaste){
						GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca +","+(planoDeReferencia*-1)+
							     ","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+(profundidadeZ + bottomAllowance))+ ","
							     +","+(comprimentoCavidade - (2*sideAllowance)) + ","+ (larguraCavidade - (2*sideAllowance))
							     + ","+ (raioCavidade - sideAllowance)+ "," + xCentro +","+ yCentro +","+ "0" + ","+
							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
						     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";
						
							lineNumber = lineNumber + 10;
						}
						
						if (raioCavidade >= raioFerramenta && !(raioFerramenta == raioMaiorDesbaste)){
							
							
							
							DeterminarMovimentacao dm = new DeterminarMovimentacao();
							ArrayList<Path> trajetoriasCantosCavidade = dm.getTrajetoriasCantosDaCavidade(wsTmp); 
							
							System.out.println("Size cantos " + trajetoriasCantosCavidade.size());
							
							   for(int j = 0; j < trajetoriasCantosCavidade.size(); j++)
								{
									double xAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getX();
									double yAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getY();
									double zAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getZ();
								
									if (trajetoriasCantosCavidade.get(j).getClass().equals(LinearPath.class) ){
										int tipoDeMovimento = 0;
										
										if(((LinearPath)trajetoriasCantosCavidade.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
										else if (((LinearPath)trajetoriasCantosCavidade.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}
										
										GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+zAux + "\n";
										
										lineNumber = lineNumber + 10;
									}
									
									if (trajetoriasCantosCavidade.get(j).getClass().equals(CircularPath.class) ){
									
										double rAux = ((CircularPath)trajetoriasCantosCavidade.get(j)).getRadius();
										int senseCP = ((CircularPath)trajetoriasCantosCavidade.get(j)).getSense();
										int senseRot = 0;
										if(((CircularPath)trajetoriasCantosCavidade.get(j)).getSense() == 0){
											senseRot = 2;
										}
										else if(((CircularPath)trajetoriasCantosCavidade.get(j)).getSense() == 1){
											senseRot = 3;
										}
																				
										GCode = GCode + "N" + lineNumber + " G"+senseRot + " X"+xAux + " Y"+yAux+ " Z"+zAux +
												" CR="+rAux+
										
										
										"\n";
										
										lineNumber = lineNumber + 10;
									}
																
								}
						
						}
						
//						GCode = GCode +"N"+lineNumber + " POCKET1("+ ((planoDeReferencia*-1)+10)+","+(planoDeReferencia*-1)+
//					     ","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+profundidadeZ)+ ","
//					     +","+comprimentoCavidade + ","+ larguraCavidade + ","+ 
//					     raioCavidade+ "," + xCentro +","+ yCentro +","+ "0" + ","+
//					     feedRate + "," + feedRate+ ","+avancoVertical + ","+
//					     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";
					
					
					
						
						}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
					
										
					
				}
			    
			    if(wsTmp.getFeature().getClass().equals(CavidadeFundoArredondado.class)){
			    	// Criar determinador de movimentacao
			    }
			    
			    if(wsTmp.getFeature().getClass().equals(RanhuraPerfilQuadradoU.class)){
			    	// Criar determinador de movimentacao
			    	
			    	RanhuraPerfilQuadradoU ranhuraPQUTmp = (RanhuraPerfilQuadradoU)wsTmp.getFeature();
			    	int GAux = 0;
			    	
			    	this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
					
					GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
					lineNumber = lineNumber + 10;
					
					GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
					lineNumber = lineNumber + 10;
					
					GCode = GCode +"N"+lineNumber+ " M6" + "\n";
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M8" + "\n";
						lineNumber = lineNumber + 10;
					}
					
					if(wsTmp.getFerramenta().getClass().equals(BullnoseEndMill.class))
					{
						System.out.println("A ferramenta eh um BullNose");
						DeterminarMovimentacao dm = new DeterminarMovimentacao();
						ArrayList<ArrayList<Path>> arrayArrayPaths = dm.getTrajetoriasAcabamentoRPQU(wsTmp);
						
						ArrayList<Path> arrayEsqInf = arrayArrayPaths.get(0);
						
						if(((Ranhura)wsTmp.getFeature()).getEixo() == Ranhura.HORIZONTAL)
						{
							GCode = GCode +"N" + lineNumber + " G19" + "\n";	
						}
						 
						for(int j = 0; j < arrayEsqInf.size(); j++)
						{
						
							double xAux = arrayEsqInf.get(j).getFinalPoint().getX();
							double yAux = arrayEsqInf.get(j).getFinalPoint().getY();
							double zAux = arrayEsqInf.get(j).getFinalPoint().getZ();
							
							if (arrayEsqInf.get(j).getClass().equals(LinearPath.class) ){
								int tipoDeMovimento = 0;
								
								if(((LinearPath)arrayEsqInf.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
								else if (((LinearPath)arrayEsqInf.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}
								
								GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+(zAux) + "\n";
								
								lineNumber = lineNumber + 10;
							}
							
							if (arrayEsqInf.get(j).getClass().equals(CircularPath.class) ){
							
								double rAux = ((CircularPath)arrayEsqInf.get(j)).getRadius();
								int senseCP = ((CircularPath)arrayEsqInf.get(j)).getSense();
								int senseRot = 0;
								if(((CircularPath)arrayEsqInf.get(j)).getSense() == 0){
									senseRot = 2;
								}
								else if(((CircularPath)arrayEsqInf.get(j)).getSense() == 1){
									senseRot = 3;
								}
																		
								GCode = GCode + "N" + lineNumber + " G"+senseRot + " X"+xAux + " Y"+yAux+ " Z"+zAux +
										" CR="+rAux+
								
								
								"\n";
								
								lineNumber = lineNumber + 10;
							}
														
						}
						
						if(((Ranhura)wsTmp.getFeature()).getEixo() == Ranhura.HORIZONTAL)
						{
							GCode = GCode +"N" + lineNumber + " G17" + "\n";	
						}
						
						
						
					}
					
					else
					{

						System.out.println("A ferramenta nao eh um BullNose");
						DeterminarMovimentacao dm = new DeterminarMovimentacao();
						
						ArrayList<ArrayList<Path>> arrayArrayPaths = dm.getTrajetoriasRanhuraPerfilQuadradoU(wsTmp);
						System.out.println("Quantidade de Trajetorias possiveis: " + arrayArrayPaths.size());
						ArrayList<Path> arrayEsqInf = arrayArrayPaths.get(0);
						System.out.println("Quantidade de Movimentacoes: " + arrayEsqInf.size());
						
						for(int j = 0; j < arrayEsqInf.size(); j++)
						{
							double xAux = arrayEsqInf.get(j).getFinalPoint().getX();
							double yAux = arrayEsqInf.get(j).getFinalPoint().getY();
							double zAux = arrayEsqInf.get(j).getFinalPoint().getZ();
							
							if (arrayEsqInf.get(j).getClass().equals(LinearPath.class) ){
								int tipoDeMovimento = 0;
								
								if(((LinearPath)arrayEsqInf.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
								else if (((LinearPath)arrayEsqInf.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}
								
								GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+(zAux) + "\n";
								
								lineNumber = lineNumber + 10;
							}
							
						}
						
						
					}
					   
					   if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M9" + "\n";
							lineNumber = lineNumber + 10;
						}
										
			    }
			    
			    if (wsTmp.getFeature().getClass().equals(Ranhura.class))
				{
			    	Ranhura ranhuraTmp = (Ranhura)wsTmp.getFeature();
					int GAux = 0;
					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
					
					GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
					lineNumber = lineNumber + 10;
					
					double positX = ranhuraTmp.getPosicaoX();
					double positY = ranhuraTmp.getPosicaoY();
					double profundidadeZ = ranhuraTmp.getProfundidade();
					double larguraRanhura = ranhuraTmp.getLargura();
					double comprimentoBloco = projeto.getBloco().getComprimento();
					double larguraBloco = projeto.getBloco().getLargura();
					
					int HORIZONTAL = 0;
					int VERTICAL = 1;
					
				
					GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
					lineNumber = lineNumber + 10;
					
					GCode = GCode +"N"+lineNumber+ " M6" + "\n";
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M8" + "\n";
						lineNumber = lineNumber + 10;
					}
					
					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacaoRanhura = DeterminarMovimentacao.getPontosMovimentacao(wsTmp);
					wsTmp.setPontosMovimentacao(movimentacaoRanhura);
					System.out.println("Quantidade de vetores " + ((Vector)wsTmp.getPontosMovimentacao()).size());
					Vector pontos = (Vector)wsTmp.getPontosMovimentacao().get(0);
					
					System.out.println("Quantidade de movimentacoes caso 0" + pontos.size());
										
							
					for (int i1 = 0; i1 < pontos.size(); i1++){
						
						Ponto pontoAux = (Ponto)pontos.get(i1);
						double xAux = pontoAux.getX();
						double yAux = pontoAux.getY();
						double zAux = pontoAux.getZ();
						
						GCode = GCode +"N" + lineNumber + " G1" + " X"+xAux+ " Y" +yAux + " Z"+(-zAux) + "\n";
						lineNumber = lineNumber + 10;
						
												
					}
					
					GCode = GCode +"N" + lineNumber + " G0" + " Z" + alturaSeguranca + "\n";
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
			    	
				}
			    
			    anterior = wsTmp.getFeature();
			    
				//}
				
			}
		}
		
		
		
		GCode = GCode +"N" + lineNumber + " M30" + "\n";
		lineNumber = lineNumber + 10;
		
				
		return GCode;
		
		
	}
	
	/*
	public ArrayList<String> GenerateGCode(){
		
		
		ArrayList<String> ArrayLines = new ArrayList<String>();
		
		double alturaSeguranca = 5.0;
		
		String LineAux = ("N" + lineNumber + " G54");
		ArrayLines.add(LineAux);
		lineNumber = lineNumber + 10;
		
		
		//LineAux = ("N"+lineNumber+ " G1" + " Z"+alturaSeguranca);
		//ArrayLines.add(LineAux);
		//lineNumber = lineNumber + 10;
		
				
		Feature anterior = null;
		
		for (int index = 0; index < this.Workingsteps.size(); index++)
		{
					
			Vector workinstepsFaceTmp = (Vector)this.Workingsteps.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				String LineAuxWS = null;
				
				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
				
				if ((anterior == null) || !(wsTmp.getFeature().equals(anterior))){
				
				if (wsTmp.getFeature().getTipo() == Feature.FURO)
				{
					Furo furoTmp = (Furo)wsTmp.getFeature();
					int GAux = 0;
					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
										
					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					double positX = furoTmp.getPosicaoX();
					double positY = furoTmp.getPosicaoY();
					double profundidadeZ = furoTmp.getProfundidade();
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double alturaBloco = projeto.getBloco().getProfundidade();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					
					
					
					
					LineAuxWS = ("N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
										
					LineAuxWS = ("N"+lineNumber+ " G0" + " X"+positX + " Y"+positY);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					LineAuxWS = ("N"+lineNumber+ " CYCLE81("+(alturaBloco+10)+", "+alturaBloco+", "
							     +alturaSeguranca+ ", "+(alturaBloco-profundidadeZ)+", " +")");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					//LineAuxWS = ("N"+lineNumber+ " G1" + " Z"+(profundidadeZ + alturaSeguranca));
					//ArrayLines.add(LineAuxWS);
					//lineNumber = lineNumber + 10;
					
					//LineAuxWS = ("N"+lineNumber+ " G1" + " Z"+(-profundidadeZ - alturaSeguranca));
					//ArrayLines.add(LineAuxWS);
					//lineNumber = lineNumber + 10;
					
				}
				
			    if (wsTmp.getFeature().getTipo() == Feature.CAVIDADE)
				{
			    	Cavidade cavidadeTmp = (Cavidade)wsTmp.getFeature();
					int GAux = 0;
					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
					
					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					double positX = cavidadeTmp.getPosicaoX();
					double positY = cavidadeTmp.getPosicaoY();
					double comprimentoCavidade = cavidadeTmp.getComprimento();
					double larguraCavidade = cavidadeTmp.getLargura();
					double xCentro = (cavidadeTmp.getPosicaoX()+ (comprimentoCavidade/2));
					double yCentro = (cavidadeTmp.getPosicaoY()+ (larguraCavidade/2));
					double raioCavidade = cavidadeTmp.getRaio();
					double profundidadeZ = cavidadeTmp.getProfundidade()*-1;
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double planoDeReferencia = cavidadeTmp.getPosicaoZ();
					//double alturaBloco = projeto.getBloco().getProfundidade();
					double avancoVertical = wsTmp.getCondicoesUsinagem().getAp();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double velCorte = wsTmp.getCondicoesUsinagem().getVc();
					double tolerancia = wsTmp.getFeature().getTolerancia();
											
					LineAuxWS = ("N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					if (wsTmp.getFeature().isAcabamento()){
						
						LineAuxWS = ("N"+lineNumber + "POCKET1("+ ((planoDeReferencia*-1)+10)+", "+(planoDeReferencia*-1)+
							     ", "+alturaSeguranca + ", "+((planoDeReferencia*-1)+profundidadeZ)+ ", "
							     +", "+comprimentoCavidade + ", "+ larguraCavidade + ", "+ 
							     raioCavidade+ ", " + xCentro +", "+ yCentro +", "+ "0" + ", "+
							     feedRate + ", " + feedRate+ ", "+avancoVertical + ", "+
							     "2"+ ", " + tolerancia + ", " + "1" + ", "+ ", " + ", "+ ", " + ")" 
							     );
					
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
										
					}
					
					else{
						LineAuxWS = ("N"+lineNumber + " POCKET1("+ ((planoDeReferencia*-1)+10)+","+(planoDeReferencia*-1)+
							     ","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+profundidadeZ)+ ","
							     +","+comprimentoCavidade + ","+ larguraCavidade + ","+ 
							     raioCavidade+ "," + xCentro +","+ yCentro +","+ "0" + ","+
							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
							     "2"+ "," +"," + "0" + ","+ "," + ","+")" 
							     );
					
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
						
						}

				
					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					
				}
			    
			    if (wsTmp.getFeature().getTipo() == Feature.RANHURA)
				{
			    	Ranhura ranhuraTmp = (Ranhura)wsTmp.getFeature();
					int GAux = 0;
					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					
					
					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}
					
					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					double positX = ranhuraTmp.getPosicaoX();
					double positY = ranhuraTmp.getPosicaoY();
					double profundidadeZ = ranhuraTmp.getProfundidade();
					double larguraRanhura = ranhuraTmp.getLargura();
					double comprimentoBloco = projeto.getBloco().getComprimento();
					double larguraBloco = projeto.getBloco().getLargura();
					
					int HORIZONTAL = 0;
					int VERTICAL = 1;
					
					LineAuxWS = ("N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					
					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					Vector pontos = (Vector)wsTmp.getPontosMovimentacao().get(0);
					
					System.out.println(pontos.size());
					
							
					for (int i1 = 0; i1 < pontos.size(); i1++){
						
						Ponto pontoAux = (Ponto)pontos.get(i1);
						double xAux = pontoAux.getX();
						double yAux = pontoAux.getY();
						double zAux = pontoAux.getZ();
						
						LineAux = ("N" + lineNumber + " G1" + " X"+xAux+ 
								" Y" +yAux + " Z"+(-zAux));
						ArrayLines.add(LineAux);
						lineNumber = lineNumber + 10;
						
												
					}
					
					LineAux = ("N" + lineNumber + " G0" + " Z" + alturaSeguranca);
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
					
					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;
			    	
				}
			    
			    anterior = wsTmp.getFeature();
			    
				}
				
			}
		}
		
		
		
		LineAux = ("N" + lineNumber + " M30");
		ArrayLines.add(LineAux);
		lineNumber = lineNumber + 10;
		
		
		
		
		return ArrayLines;
	} */

}
