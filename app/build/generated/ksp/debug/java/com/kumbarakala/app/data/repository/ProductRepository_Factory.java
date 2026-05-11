package com.kumbarakala.app.data.repository;

import android.content.Context;
import com.kumbarakala.app.data.local.dao.ProductDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ProductRepository_Factory implements Factory<ProductRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<ProductDao> productDaoProvider;

  public ProductRepository_Factory(Provider<Context> contextProvider,
      Provider<ProductDao> productDaoProvider) {
    this.contextProvider = contextProvider;
    this.productDaoProvider = productDaoProvider;
  }

  @Override
  public ProductRepository get() {
    return newInstance(contextProvider.get(), productDaoProvider.get());
  }

  public static ProductRepository_Factory create(Provider<Context> contextProvider,
      Provider<ProductDao> productDaoProvider) {
    return new ProductRepository_Factory(contextProvider, productDaoProvider);
  }

  public static ProductRepository newInstance(Context context, ProductDao productDao) {
    return new ProductRepository(context, productDao);
  }
}
