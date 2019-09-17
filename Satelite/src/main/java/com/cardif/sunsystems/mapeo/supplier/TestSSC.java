package com.cardif.sunsystems.mapeo.supplier;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class TestSSC
{

	public static void main(String[] args)
	{
		System.out.println("Inicio");
		
		try
		{
			File file = new File("C:\\JOSE_MANUEL\\CARDIF_SVN_Repository_NEW\\REPO_SATELITE_FINANZAS_BRANCH\\Satelite 2.0\\src\\main\\java\\com\\cardif\\sunsystems\\plantillas\\supplierQueryIN.xml");
			System.out.println("======>>>file exists? " + file.exists());
			
			JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.supplier.SSC.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			com.cardif.sunsystems.mapeo.supplier.SSC supplier = (com.cardif.sunsystems.mapeo.supplier.SSC) unmarshaller.unmarshal(file);
			System.out.println("===========supplier: " + supplier);
			
			System.out.println("supplier.getPayload(): " + supplier.getPayload());
			System.out.println("supplier.getPayload().getSupplier(): " + supplier.getPayload().getSupplier());
			
			for (SSC.Payload.Supplier supplierObj : supplier.getPayload().getSupplier())
			{
				System.out.println("supplierObj.getBankDetails(): " + supplierObj.getBankDetails());
				System.out.println("supplierObj.getSupplierAddress(): " + supplierObj.getSupplierAddress());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Fin");
	}
	
}
