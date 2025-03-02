package org.example.com.archunittest

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent.satisfied
import com.tngtech.archunit.lang.SimpleConditionEvent.violated
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.hibernate.envers.AuditTable
import org.junit.jupiter.api.Test

class AuditTableNamingTest {

    @Test
    fun auditTableMustEndWithLog() {
        // org.example 하위의 모든 java class import
        val importedClasses: JavaClasses = ClassFileImporter().importPackages("org.example")
        importedClasses.forEach { println("import class name ${it.name}") }

        val auditTableCondition = object : ArchCondition<JavaClass>("have an @AuditTable name ending with '_log'") {
            override fun check(item: JavaClass, events: ConditionEvents) {
                val auditTable = item.getAnnotationOfType(AuditTable::class.java)
                // @AuditTable(name = "테이블명") 값 가져오기
                val tableName = auditTable.value

                // _log 로 시작하는 지 검사
                val result = if (tableName.endsWith("_log")) {
                    satisfied(item, "Class ${item.name} has @AuditTable with name '$tableName', and role('_log') is satisfied")
                } else {
                    violated(item, "Class ${item.name} has @AuditTable with name '$tableName', but it should end with '_log'")
                }

                // 결과 반환
                events.add(result)
            }
        }

        val rule: ArchRule = ArchRuleDefinition.classes()
            .that()
            .areAnnotatedWith(AuditTable::class.java)
            .should(auditTableCondition)

        rule.check(importedClasses) // 규칙 실행
    }
}