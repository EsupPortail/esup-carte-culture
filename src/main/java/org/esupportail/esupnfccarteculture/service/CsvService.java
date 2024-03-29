/**
 * Licensed to ESUP-Portail under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * ESUP-Portail licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esupportail.esupnfccarteculture.service;

import org.esupportail.esupnfccarteculture.entity.ExportAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class CsvService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	

	public void csvWriteExport(Writer writer, List<ExportAll> exports) throws IOException {

		log.info("Generate CSV for " + exports.size() + " etudiants");
		
		Field[] attributes = ExportAll.class.getDeclaredFields();
		
		final String[] header = new String[attributes.length];
		int i = 0;
		for (Field field : attributes) {
			header[i]=field.getName();
			i++;
		}
		
		ICsvBeanWriter beanWriter =  new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

		beanWriter.writeHeader(header);
		
		for (ExportAll export : exports) {
			beanWriter.write(export, header);
		}
		
		beanWriter.close();
		log.info("Generate CSV OK");
	}

	
}
