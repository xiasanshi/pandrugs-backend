/*
 * #%L
 * PanDrugsDB Backend
 * %%
 * Copyright (C) 2015 Fátima Al-Shahrour, Elena Piñeiro, Daniel Glez-Peña and Miguel Reboiro-Jato
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.pandrugsdb.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.ei.sing.pandrugsdb.service.DefaultGeneDrugServiceUnitTest;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultRegistrationServiceUnitTest;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultSessionServiceUnitTest;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultUserServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	DefaultSessionServiceUnitTest.class,
	DefaultRegistrationServiceUnitTest.class,
	DefaultUserServiceUnitTest.class,
	DefaultGeneDrugServiceUnitTest.class
})
public class ServiceUnitTestSuite {
}
