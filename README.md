# Hibernate: Paged data & count

This repository shows that after upgrading to Hibernate version >= 6.6.4.Final native quries with scalar values fail

- Working commit: [b5cc5da3480c59ee0bf3292688ecf6fd7ccefb61](https://github.com/durimkryeziu/paged-data-and-count-hibernate/commit/b5cc5da3480c59ee0bf3292688ecf6fd7ccefb61)
```
Hibernate: SELECT *, COUNT(*) OVER() AS total_count FROM books WHERE title ILIKE ? fetch first ? rows only
```
- Nonworking commit: [2e881d1aa936555a02eb13105cca2fecb0ed4c95](https://github.com/durimkryeziu/paged-data-and-count-hibernate/commit/2e881d1aa936555a02eb13105cca2fecb0ed4c95)
```
Hibernate: SELECT *, COUNT(*) OVER() AS total_count FROM books WHERE title ILIKE ? fetch first ? rows only

class java.lang.Long cannot be cast to class [Ljava.lang.Object; (java.lang.Long and [Ljava.lang.Object; are in module java.base of loader 'bootstrap')
java.lang.ClassCastException: class java.lang.Long cannot be cast to class [Ljava.lang.Object; (java.lang.Long and [Ljava.lang.Object; are in module java.base of loader 'bootstrap')
	at com.example.hibernate.HibernateUtils.count(HibernateUtils.java:31)
	at com.example.hibernate.HibernateUtils.getResultListAndCount(HibernateUtils.java:22)
	at com.example.hibernate.BookRepositoryCustomImpl.findByTitle(BookRepositoryCustomImpl.java:32)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:569)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:727)
	at com.example.hibernate.BookRepositoryCustomImpl$$SpringCGLIB$$0.findByTitle(<generated>)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:569)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:277)
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170)
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158)
	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:515)
	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:284)
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:731)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:174)
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:149)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:165)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223)
	at jdk.proxy3/jdk.proxy3.$Proxy129.findByTitle(Unknown Source)
	at com.example.hibernate.BookRepositoryTest.findByTitle(BookRepositoryTest.java:27)
	at java.base/java.lang.reflect.Method.invoke(Method.java:569)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
  ```
