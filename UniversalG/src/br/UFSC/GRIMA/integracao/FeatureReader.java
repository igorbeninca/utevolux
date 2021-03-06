package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SMachining_schema.EB_spline_curve;
import jsdai.SMachining_schema.EBezier_curve;
import jsdai.SMachining_schema.EClosed_pocket;
import jsdai.SMachining_schema.EConical_hole_bottom;
import jsdai.SMachining_schema.EDiameter_taper;
import jsdai.SMachining_schema.EFlat_hole_bottom;
import jsdai.SMachining_schema.EFlat_with_radius_hole_bottom;
import jsdai.SMachining_schema.EGeneral_profile;
import jsdai.SMachining_schema.EMachining_feature;
import jsdai.SMachining_schema.EMachining_workingstep;
import jsdai.SMachining_schema.EPartial_circular_profile;
import jsdai.SMachining_schema.ERound_hole;
import jsdai.SMachining_schema.ERounded_u_profile;
import jsdai.SMachining_schema.ESlot;
import jsdai.SMachining_schema.ESpherical_hole_bottom;
import jsdai.SMachining_schema.ESquare_u_profile;
import jsdai.SMachining_schema.EStep;
import jsdai.SMachining_schema.EThrough_bottom_condition;
import jsdai.SMachining_schema.EVee_profile;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class FeatureReader {

	
public static Feature getFeature(EMachining_workingstep wkstep) throws SdaiException{
		
		Feature feature = null;
		
		if (wkstep.getIts_feature(null).isKindOf(ERound_hole.class)) {
			
			ERound_hole round_hole = (ERound_hole) wkstep.getIts_feature(null);
			
		    boolean isConico = false;
			
			try{
				
				round_hole.getChange_in_diameter(null);
				
				isConico = true;
				
			}catch(SdaiException e){
				
				if(e.getMessage().equals("VA_NSET - Value not set")){
					isConico = false;
				}
			}
			
			
			if(round_hole.getBottom_condition(null).isKindOf(EFlat_with_radius_hole_bottom.class)){
				
				feature = FuroBaseArredondadaReader.getFuro(round_hole);
				
			}else if(round_hole.getBottom_condition(null).isKindOf(EConical_hole_bottom.class)){
				
				feature = FuroBaseConicaReader.getFuro(round_hole);
				
			}else if(round_hole.getBottom_condition(null).isKindOf(ESpherical_hole_bottom.class)){
				
				feature = FuroBaseEsfericaReader.getFuro(round_hole);
			
			}else if (isConico) {
				
				System.out.println("CONICOOOOOOO");
				
				feature = FuroConicoReader.getFuro(round_hole);
				
			}else if(round_hole.getBottom_condition(null).isKindOf(EFlat_hole_bottom.class)
					|| round_hole.getBottom_condition(null).isKindOf(EThrough_bottom_condition.class)){
				
				feature = FuroBasePlanaReader.getFuro(round_hole);
				
			}else {
				
				System.out.println("Tipo de Round Hole Desconhecio!!! Bottom Condition: " + round_hole.getBottom_condition(null) );
				
			}
			
		}else if (wkstep.getIts_feature(null).isKindOf(ESlot.class)) {
			
			ESlot slot = (ESlot)wkstep.getIts_feature(null);
			
			if(slot.getSwept_shape(null).isKindOf(EVee_profile.class)){  
				
				feature = RanhuraPerfilVeeReader.getRanhura(slot);
				
			}else if(slot.getSwept_shape(null).isKindOf(ERounded_u_profile.class)){ 
				
				feature = RanhuraPerfilRoundedUReader.getRanhura(slot);
				
			}else if(slot.getSwept_shape(null).isKindOf(EPartial_circular_profile.class)){ 
				
				feature = RanhuraPerfilCircularParcialReader.getRanhura(slot);
				
			}else if(slot.getSwept_shape(null).isKindOf(ESquare_u_profile.class)){ 

				ESquare_u_profile u = (ESquare_u_profile)slot.getSwept_shape(null);

				boolean isPerfilQuadradoU = false;

				try{

					u.getFirst_angle(null);

					isPerfilQuadradoU = true;

				}catch(SdaiException e){

					if(e.getMessage().equals("VA_NSET - Value not set")){

						try{

							u.getFirst_radius(null);

							isPerfilQuadradoU = true;

						}catch(SdaiException e2){

							if(e2.getMessage().equals("VA_NSET - Value not set")){

								isPerfilQuadradoU = false;
							}

						}

					}
				}

				if(isPerfilQuadradoU){
					
					System.out.println("RANHURA PERFIL QUADRADO U");
					
					feature = RanhuraPerfilQuadradoUReader.getRanhura(slot);
					
				}
				else{
					
					System.out.println("RANHURA NORMAL");
					
					feature = RanhuraReader.getRanhura(slot);
				}
				
			}else if(((EB_spline_curve)((EGeneral_profile)slot.getSwept_shape(null)).getIts_profile(null)).isKindOf(EBezier_curve.class)){ 
				
				feature = RanhuraPerfilBezierReader.getRanhura(slot);
			
			}else {
				
				System.out.println("Tipo de Ranhura Desconhecido! Swept Shape : " + slot.getSwept_shape(null));
			}
			
		} else if (wkstep.getIts_feature(null).isKindOf(EClosed_pocket.class)) {
			
			EClosed_pocket closed_pocket = (EClosed_pocket)wkstep.getIts_feature(null);
			
			boolean isCavidade = false;
			
			try{
				
				closed_pocket.getPlanar_radius(null);
				
			}catch(SdaiException e){
				
				if(e.getMessage().equals("VA_NSET - Value not set")){
					isCavidade = true;
				}
			}
			
			if(isCavidade){
			
				feature = CavidadeReader.getCavidade(closed_pocket);
				
//			}else if(closed_pocket.getPlanar_radius(null).getTheoretical_size(null)!= 0){
			}else{
				
				feature = CavidadeFundoArredondadoReader.getCavidade(closed_pocket);
				
			}
			
		}else if (wkstep.getIts_feature(null).isKindOf(EStep.class)){
			
			EStep step = (EStep)wkstep.getIts_feature(null);
			
			feature = DegrauReader.getDegrau(step);
			
		}else{
			
			System.out.println("Não é furo, nem ranhura, nem cavidade, nem degrau = " + wkstep.getIts_feature(null));
			
		}
		
		
		EMachining_feature eFeature = (EMachining_feature) wkstep.getIts_feature(null);
		
		double zLinhaX = eFeature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(1);
		double zLinhaY = eFeature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(2);
		double zLinhaZ = eFeature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> axis = new ArrayList<Double>();
		axis.add(zLinhaX);
		axis.add(zLinhaY);
		axis.add(zLinhaZ);
		
		zLinhaX = eFeature.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(1);
		zLinhaY = eFeature.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(2);
		zLinhaZ = eFeature.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> refDirection = new ArrayList<Double>();
		refDirection.add(zLinhaX);
		refDirection.add(zLinhaY);
		refDirection.add(zLinhaZ);
		
		double x = eFeature.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(1);
		double y = eFeature.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(2);
		double z = eFeature.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(3);
		
		Point3d coordinates = new Point3d(x,y,z);
		
		Axis2Placement3D position = new Axis2Placement3D(coordinates,axis,refDirection);
		
		feature.setPosition(position);
		
		return feature;
	}
}
